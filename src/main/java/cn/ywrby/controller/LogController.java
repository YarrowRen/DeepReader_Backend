package cn.ywrby.controller;

import cn.ywrby.domain.Log;
import cn.ywrby.domain.User;
import cn.ywrby.domain.UserClass;
import cn.ywrby.res.ResultResponse;
import cn.ywrby.service.LogService;
import cn.ywrby.service.UserService;
import cn.ywrby.utils.Constants;
import cn.ywrby.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @GetMapping("/log/userBookLog")
    public ResultResponse getUserBookLog(@RequestParam("token") String token,@RequestParam("bookId") int bookId){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue= JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //如果合法则返回用户信息
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");
            User user = userService.findUserByUsername(username);
            Log log = logService.getUserBookLog(user.getId(), bookId);
            res.setData(log);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }
        return res;
    }


    @GetMapping("/log/viewBook")
    private ResultResponse viewBook(@RequestParam("token") String token,@RequestParam("bookId") int bookId){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue= JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //如果合法则返回用户信息
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");
            User user = userService.findUserByUsername(username);
            logService.viewBook(user.getId(), bookId);
            res.setData(Constants.MESSAGE_OK);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }
        return res;
    }

    @GetMapping("/log/viewBookAtExam")
    private ResultResponse viewBookAtExam(@RequestParam("token") String token,@RequestParam("bookId") int bookId){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue= JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //如果合法则返回用户信息
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");
            User user = userService.findUserByUsername(username);
            logService.viewBookAtExam(user.getId(), bookId);
            res.setData(Constants.MESSAGE_OK);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }
        return res;
    }

    @GetMapping("/log/userDataCount")
    private ResultResponse userDataCount(@RequestParam("token") String token){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue= JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //如果合法则返回用户信息
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");
            User user = userService.findUserByUsername(username);
            Map<String,Double> dataCount=logService.userDataCount(user.getId());
            res.setData(dataCount);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }
        return res;
    }

}
