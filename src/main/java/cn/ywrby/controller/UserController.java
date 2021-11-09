package cn.ywrby.controller;

import cn.ywrby.domain.Token;
import cn.ywrby.domain.User;
import cn.ywrby.res.ResultResponse;
import cn.ywrby.service.UserService;
import cn.ywrby.utils.Constants;
import cn.ywrby.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

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
            List<String> roles= Arrays.asList("admin","editor");
            user.setRoles(roles);

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


}
