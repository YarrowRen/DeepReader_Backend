package cn.ywrby.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ywrby.domain.AnswerForm;
import cn.ywrby.domain.QuestionForm;
import cn.ywrby.domain.User;
import cn.ywrby.res.ResultResponse;
import cn.ywrby.service.BookService;
import cn.ywrby.service.DeepQuestionService;
import cn.ywrby.service.UserService;
import cn.ywrby.utils.Constants;
import cn.ywrby.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class DeepQuestionController {

    @Autowired
    BookService bookService;

    @Autowired
    DeepQuestionService dqService;

    @Autowired
    UserService userService;

    @GetMapping("/dq/getQuestionFormByBookId")
    public ResultResponse getQuestionFormByBookId(@RequestParam("bookId") String bookId){
        ResultResponse res=new ResultResponse();
        List<QuestionForm> questionForms=dqService.getQuestionFormByBookId(bookId);
        res.setData(questionForms);
        res.setCode(Constants.STATUS_OK);
        res.setMessage(Constants.MESSAGE_OK);
        return res;

    }

    @PostMapping("/dq/submitAnswer")
    public ResultResponse updateQuestionForm(@RequestParam("token") String token,@RequestParam("answerForm") String answerFormStr){
        ResultResponse res=new ResultResponse();
        //解析JSON字符串，取得回答表单对象
        AnswerForm answerForm = dqService.getAnswerForm(answerFormStr);
        //验证token的合法和有效性
        String tokenValue= JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //获取用户登录账户（学生号）
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");
            User user = userService.findUserByUsername(username);
            answerForm.setUserId(user.getId());
            answerForm.setFinish_time(new Date());
            boolean result = dqService.insertAnswerForm(answerForm);
            if(result){
                //更新用户练习状态
                userService.insertUserAnswer(user.getId(),answerForm.getBookId());
                res.setCode(Constants.STATUS_OK);
            }else {
                //否则返回错误状态码
                res.setCode(Constants.STATUS_FAIL);
                res.setMessage(Constants.MESSAGE_FAIL+"提交失败，请重新提交");
            }
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL+"登录信息已失效，请重新登录后提交");
        }
        return res;
    }
}
