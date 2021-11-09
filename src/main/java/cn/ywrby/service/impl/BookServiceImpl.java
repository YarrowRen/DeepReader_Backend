package cn.ywrby.service.impl;

import cn.ywrby.domain.Book;
import cn.ywrby.domain.Classify;
import cn.ywrby.mapper.BookMapper;
import cn.ywrby.mapper.ClassifyMapper;
import cn.ywrby.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    ClassifyMapper classifyMapper;

    @Override
    public List<Book> getBookListByUsername(String username) {
        List<Book> bookList = bookMapper.getBookListByUsername(username);
        return bookList;
    }

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
}
