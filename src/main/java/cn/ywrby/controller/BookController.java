package cn.ywrby.controller;

import cn.hutool.json.JSONObject;
import cn.ywrby.domain.Book;
import cn.ywrby.domain.Classify;
import cn.ywrby.domain.SimpleClassify;
import cn.ywrby.res.ResultResponse;
import cn.ywrby.service.BookService;
import cn.ywrby.utils.Constants;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/book/classifyAndBookList")
    public ResultResponse getClassifyAndBookList(){
        ResultResponse res=new ResultResponse();
        try {
            List<Classify> classifyList = bookService.getClassifyAndBookList();
            res.setData(classifyList);
            res.setCode(Constants.STATUS_OK);
            res.setMessage(Constants.MESSAGE_OK);
        }catch (Exception e){
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL+e.getMessage());
            res.setData("fail");
            e.printStackTrace();
        }
        return res;
    }


    @GetMapping("/book/list")
    public ResultResponse bookList(@RequestParam(required=true, defaultValue = "1")Integer page, @RequestParam(required=false,defaultValue="10")Integer pageSize){
        ResultResponse res=new ResultResponse();
        //调用service层方法获得用户列表
        List<Book> list=bookService.getBookList(page,pageSize);
        //获取分页信息对象
        PageInfo<Book> info=new PageInfo<Book>(list);

        res.setData(info);
        res.setCode(Constants.STATUS_OK);
        res.setMessage(Constants.MESSAGE_OK);
        System.out.println(info);
        return res;
    }

    @GetMapping("/book/getBookByClassify")
    public ResultResponse getBookByClassifyId(@RequestParam(required=true)Integer classifyId){
        ResultResponse res=new ResultResponse();
        //调用service层方法获得用户列表
        List<Book> list=bookService.getBookByClassifyId(classifyId);
        res.setData(list);
        res.setCode(Constants.STATUS_OK);
        res.setMessage(Constants.MESSAGE_OK);
        return res;
    }

    @GetMapping("/book/content")
    public ResultResponse getBookContent(@RequestParam(required=true)Integer bookId){
        ResultResponse res=new ResultResponse();
        String content = bookService.getBookContent(bookId);
        res.setData(content);
        res.setCode(Constants.STATUS_OK);
        res.setMessage(Constants.MESSAGE_OK);
        return res;
    }

    @GetMapping("/book/info")
    public ResultResponse getBookInfo(@RequestParam(required=true)Integer bookId){
        ResultResponse res=new ResultResponse();
        Book book = bookService.getBookInfo(bookId);
        res.setData(book);
        res.setCode(Constants.STATUS_OK);
        res.setMessage(Constants.MESSAGE_OK);
        return res;
    }

    @GetMapping("/book/classify")
    public ResultResponse getClassifyList(){
        ResultResponse res=new ResultResponse();
        List<SimpleClassify> simpleClassifyList=bookService.getClassify();
        res.setData(simpleClassifyList);
        res.setCode(Constants.STATUS_OK);
        res.setMessage(Constants.MESSAGE_OK);
        return res;
    }
}
