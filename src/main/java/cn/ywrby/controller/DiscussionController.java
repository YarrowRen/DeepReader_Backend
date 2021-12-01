package cn.ywrby.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ywrby.domain.Book;
import cn.ywrby.domain.Discussion;
import cn.ywrby.domain.KWL;
import cn.ywrby.domain.User;
import cn.ywrby.res.ResultResponse;
import cn.ywrby.service.DicussionService;
import cn.ywrby.service.UserService;
import cn.ywrby.utils.Constants;
import cn.ywrby.utils.JwtUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscussionController {

    @Autowired
    DicussionService dicussionService;

    @Autowired
    UserService userService;

    @GetMapping("/discussion/list")
    public ResultResponse getDiscussionList(@RequestParam(required=true, defaultValue = "1")Integer page, @RequestParam(required=true,defaultValue="10")Integer pageSize){
        ResultResponse res=new ResultResponse();
        List<Discussion> discussionList = dicussionService.getDiscussionList( page, pageSize);
        //获取分页信息对象
        PageInfo<Discussion> info=new PageInfo<Discussion>(discussionList);

        res.setData(info);
        res.setCode(Constants.STATUS_OK);
        res.setMessage(Constants.MESSAGE_OK);
        System.out.println(info);
        return res;

    }

    @GetMapping("/discussion/getDiscussion")
    public ResultResponse getDiscussionList(@RequestParam(required=true)Integer discussionId){
        ResultResponse res=new ResultResponse();
        Discussion discussion = dicussionService.getDiscussionById(discussionId);
        res.setData(discussion);
        res.setCode(Constants.STATUS_OK);
        res.setMessage(Constants.MESSAGE_OK);
        return res;

    }

    @PostMapping("/discussion/submitDiscussion")
    public ResultResponse submitDiscussion(@RequestParam("token") String token,@RequestParam("discussion") String discussionStr){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue= JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)){
            //获取用户登录账户（学生号）
            String username=tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS,"");
            User user=userService.findUserByUsername(username);
            boolean result=dicussionService.submitDiscussion(user.getId(),discussionStr);
            if (result){
                res.setCode(Constants.STATUS_OK);
            }else {
                res.setCode(Constants.STATUS_FAIL);
                res.setMessage(Constants.MESSAGE_FAIL+"提交失败");
            }
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL+"登录信息已失效，请重新登录后提交");
        }
        return res;
    }
}
