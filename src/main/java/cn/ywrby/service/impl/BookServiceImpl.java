package cn.ywrby.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.ywrby.domain.*;
import cn.ywrby.mapper.BookMapper;
import cn.ywrby.mapper.ClassifyMapper;
import cn.ywrby.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    ClassifyMapper classifyMapper;



    @Override
    public List<Classify> getClassifyAndBookList() {
        List<Classify> classifyList = classifyMapper.getClassifyList();
        List<Classify> result=new LinkedList<>();
        for (Classify classify : classifyList) {
            List<Book> bookList = bookMapper.getSimplifyBookListByClassifyId(classify.getId());
            classify.setChildren(bookList);
            result.add(classify);
        }
        return result;
    }

    @Override
    public List<Book> getBookList(Integer page,Integer pageSize) {
        //获取分页插件对象
        PageHelper pageHelper=new PageHelper();
        //开始分页，指定分页参数
        PageMethod.startPage(page,pageSize);

        List<Book> bookList = bookMapper.getBookList();
        return bookList;
    }

    @Override
    public String getBookContent(Integer bookId) {
        String content = bookMapper.getBookContent(bookId);
        return content;
    }

    @Override
    public List<Book> getUserBookList(User user, Integer page, Integer pageSize) {
        //获取分页插件对象
        PageHelper pageHelper=new PageHelper();
        //开始分页，指定分页参数
        PageMethod.startPage(page,pageSize);
        //获取用户ID
        int id=user.getId();
        //获取该用户图书列表
        List<Book> bookList = bookMapper.getUserBookList(id);
        //获取该用户已阅读图书ID列表
        List<Integer> haveReadList = bookMapper.getUserHaveReadList(id);
        //获取该用户已进行过练习的图书ID列表
        List<Integer> haveAnswerList=bookMapper.getUserHaveAnswerList(id);
        //查询用户阅读过哪些图书并标记
        for (Book book: bookList){
            for(int bookId: haveReadList){
                if(book.getId()==bookId){
                    book.setHave_read(true);
                }
            }
            for(int bookId: haveAnswerList){
                if(book.getId()==bookId){
                    book.setHave_answer(true);
                }
            }
        }
        return bookList;
    }

    @Override
    public Book getBookInfo(int bookId) {
        Book book=bookMapper.getBookInfo(bookId);
        return book;
    }

    @Override
    public List<SimpleClassify>  getClassify() {
        List<Classify> classifyList = classifyMapper.getClassifyList();
        List<SimpleClassify> simpleClassifyList=new ArrayList<>();
        for(Classify classify:classifyList){
            boolean res=true;

            for (SimpleClassify simpleClassify:simpleClassifyList){
                if (classify.getName().equals(simpleClassify.getLabel())){
                    res=false;
                    simpleClassify.getChildren().add(new Work(classify.getId(),classify.getWork()));
                }
            }

            if(res){
                SimpleClassify simpleClassify=new SimpleClassify();
                simpleClassify.setLabel(classify.getName());
                simpleClassify.setChildren(new ArrayList<>());
                simpleClassify.getChildren().add(new Work(classify.getId(),classify.getWork()));
                simpleClassifyList.add(simpleClassify);
            }
        }
        System.out.println(simpleClassifyList);
        return simpleClassifyList;
    }

    @Override
    public List<Book> getBookByClassifyId(int classifyId) {
        List<Book> bookList = bookMapper.getBookByClassifyId(classifyId);
        return bookList;
    }


}
