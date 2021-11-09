package cn.ywrby.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ywrby.domain.Book;
import cn.ywrby.domain.Token;
import cn.ywrby.domain.User;
import cn.ywrby.domain.UserClass;
import cn.ywrby.res.ResultResponse;
import cn.ywrby.service.BookService;
import cn.ywrby.service.UserService;
import cn.ywrby.utils.Constants;
import cn.ywrby.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/stu/clsInfo")
    public ResultResponse clsInfo(@RequestParam("token") String token){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue= JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //如果合法则返回用户信息
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");

            UserClass userClass = userService.findClassByUsername(username);
            res.setData(userClass);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }

        return res;
    }

    @PostMapping("/stu/updateStuInfo")
    public ResultResponse updateStuInfo(@RequestBody String jsonStr){
        ResultResponse res=new ResultResponse();
        //解析JSON字符串，取得用户名与密码信息以及登录token
        JSONObject jsonObject= JSONUtil.parseObj(jsonStr);
        String name= (String) jsonObject.get("name");
        String oldPassword= (String) jsonObject.get("oldPassword");
        String newPassword= (String) jsonObject.get("newPassword");
        String token= (String) jsonObject.get("token");

        //验证token的合法和有效性
        String tokenValue=JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)){
            //获取用户登录账户（学生号）
            String username=tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS,"");
            boolean result = userService.updateUserInfo(username, name, oldPassword, newPassword);
            if(result){
                //密码修改成功，返回更新后的相关值
                res.setCode(Constants.STATUS_OK);
                res.setMessage(Constants.MESSAGE_OK);
                res.setData(name);
            }else {
                //密码错误，修改失败
                res.setCode(Constants.STATUS_FAIL);
                res.setMessage(Constants.MESSAGE_FAIL+"用户名与密码不匹配");
            }
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL+"登录信息已失效，请重新登录");
        }
        return res;

    }


    @GetMapping("/stu/bookList")
    public ResultResponse stuBookInfo(@RequestParam("token") String token){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue= JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //如果合法则返回用户信息
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");
            List<Book> bookList = bookService.getBookListByUsername(username);

            res.setData(bookList);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }
        return res;
    }

    @PostMapping("/stu/updateBookList")
    public ResultResponse updateStuBookList(@RequestParam("token") String token, @RequestParam("bookList[]") List<String> bookList){
        ResultResponse res=new ResultResponse();
        //验证token的合法和有效性
        String tokenValue= JwtUtils.verify(token);
        if(tokenValue!=null && tokenValue.startsWith(JwtUtils.TOKEN_SUCCESS)) {
            //如果合法则返回用户信息
            String username = tokenValue.replaceFirst(JwtUtils.TOKEN_SUCCESS, "");
            boolean result = userService.setUserBookList(username, bookList);
            if (result) {
                res.setData("学生图书列表添加成功");
                res.setCode(Constants.STATUS_OK);
                res.setMessage(Constants.MESSAGE_OK+" : 学生图书列表添加成功");
            }else {
                res.setCode(Constants.STATUS_FAIL);
                res.setMessage(Constants.MESSAGE_FAIL+"学生已添加图书信息，添加失败");
            }
        }else {
            //否则返回错误状态码
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }
        return res;
    }

}
