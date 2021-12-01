package cn.ywrby.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ywrby.domain.*;
import cn.ywrby.res.ResultResponse;
import cn.ywrby.service.BookService;
import cn.ywrby.service.UserService;
import cn.ywrby.utils.Constants;
import cn.ywrby.utils.JwtUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @PostMapping("/user/login")
    public ResultResponse login(@RequestBody User user){
        System.out.println(user);
        ResultResponse res=new ResultResponse();
        //调用Service层的UserService完成对用户名和密码的验证
        try {
            User u=userService.verify(user);
            //根据验证结果，组成响应对象返回
            if (u!=null){
                //验证成功，创建一个Token，封装到res对象中
                String token = JwtUtils.sign(user.getUsername(), "-1");
                res.setCode(Constants.STATUS_OK);
                res.setMessage(Constants.MESSAGE_OK);
                res.setData(new Token(token));
            }else {
                res.setCode(Constants.STATUS_FAIL);
                res.setMessage(Constants.MESSAGE_FAIL+"用户名与密码不匹配");
                res.setData("fail");
            }
        }catch (Exception e){
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL+e.getMessage());
            res.setData("fail");
            e.printStackTrace();
        }
        return res;
    }


    @GetMapping("/user/info")
    public ResultResponse info(@RequestParam("token") String token){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue=JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)){
            //如果合法则返回用户信息
            String username=tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS,"");
            User user=userService.findUserByUsername(username);
            user.setUsername(username);
            user.setAvatar("https://ywrbyimg.oss-cn-chengdu.aliyuncs.com/img/ywrby.png");

            res.setData(user);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }

        return res;
    }


    @PostMapping("/user/logout")
    public ResultResponse logout(@RequestHeader("X-Token") String token){
        ResultResponse res=new ResultResponse();
        //验证token的合法性和有效性
        String tokenValue=JwtUtils.verify(token);
        //获取token中的用户名
        String username=tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS,"");
        //移除Session中的登陆标记（或redis中的登录标记）

        res.setMessage("Logout success");
        res.setData("Logout success");
        res.setCode(Constants.STATUS_OK);
        return res;
    }

    @GetMapping("/user/booklist")
    public ResultResponse bookList(@RequestParam("token") String token,@RequestParam(required=true, defaultValue = "1")Integer page, @RequestParam(required=false,defaultValue="10")Integer pageSize){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue=JwtUtils.verify(token);

        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //如果合法则返回用户信息
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");
            User user = userService.findUserByUsername(username);
            user.setUsername(username);
            //调用service层方法获得用户图书列表
            List<Book> list = bookService.getUserBookList(user, page, pageSize);
            //获取分页信息对象
            PageInfo<Book> info = new PageInfo<Book>(list);
            res.setData(info);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
            System.out.println(info);
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }
        return res;
    }

    @PostMapping("/user/updateUserKWLForm")
    public ResultResponse updateUserKWLForm(@RequestParam("token") String token,@RequestParam("KWLForm") String KWLForm){
        ResultResponse res=new ResultResponse();
        //解析JSON字符串，取得用户名与密码信息以及登录token
        JSONObject jsonObject= JSONUtil.parseObj(KWLForm);
        JSONArray knows= (JSONArray) jsonObject.get("knows");
        String strKnows=knows.toString();
        JSONArray doubts = (JSONArray) jsonObject.get("doubts");
        String strDoubts=doubts.toString();
        int bookId= (int) jsonObject.get("bookId");
        int time=(int)jsonObject.get("time");
        JSONObject summary= (JSONObject) jsonObject.get("summary");
        //验证token的合法和有效性
        String tokenValue=JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)){
            //获取用户登录账户（学生号）
            String username=tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS,"");
            User user=userService.findUserByUsername(username);
            KWL kwl=new KWL();
            kwl.setBookId(bookId);
            kwl.setUserId(user.getId());
            kwl.setPreKnown(strKnows);
            kwl.setQA(strDoubts);
            kwl.setRate((Integer) summary.get("rate"));
            kwl.setSummary((String) summary.get("content"));
            kwl.setRead_time(time);
            //插入用户KWL表格
            boolean result=userService.insertKWLForm(kwl);
            //boolean result=true;
            if(result){
                //更新用户阅读状态
                userService.insertUserRead(user.getId(),bookId);
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

    @PostMapping("/user/updateQuestionForm")
    public ResultResponse updateQuestionForm(@RequestParam("token") String token,@RequestParam("questionForm") String questionForm){
        ResultResponse res=new ResultResponse();
        //解析JSON字符串，取得用户名与密码信息以及登录token
        JSONObject jsonObject= JSONUtil.parseObj(questionForm);
        int bookId= (int) jsonObject.get("bookId");
        int rate=(int)jsonObject.get("robotRate");
        String modifyQuestion=(String)jsonObject.get("modifyQuestion");
        String type=(String)jsonObject.get("questionType");
        String question=(String)jsonObject.get("questionDescribe");
        String clues=(String)jsonObject.get("questionClues");
        String answer=(String)jsonObject.get("questionAnswer");

        //验证token的合法和有效性
        String tokenValue=JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //获取用户登录账户（学生号）
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");
            User user = userService.findUserByUsername(username);
            QuestionForm QF=new QuestionForm();
            QF.setQuestion(question);
            QF.setAnswer(answer);
            QF.setModifyQuestion(modifyQuestion);
            QF.setClues(clues);
            QF.setRobotQuestion("机器人提问");
            QF.setUserId(user.getId());
            QF.setBookId(bookId);
            QF.setRate(rate);
            QF.setType(type);
            boolean result = userService.insertQuestionForm(QF);
            if(result){
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
