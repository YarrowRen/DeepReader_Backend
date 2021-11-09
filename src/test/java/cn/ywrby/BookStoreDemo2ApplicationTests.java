package cn.ywrby;

import cn.ywrby.domain.Book;
import cn.ywrby.domain.Classify;
import cn.ywrby.domain.User;
import cn.ywrby.domain.UserClass;
import cn.ywrby.mapper.BookMapper;
import cn.ywrby.mapper.ClassifyMapper;
import cn.ywrby.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookStoreDemo2ApplicationTests {

    @Autowired
    private ClassifyMapper classifyMapper;

    @Autowired
    private BookMapper bookMapper;

    @Test
    void contextLoads() {
        //List<Book> bookListByClassifyId = bookMappergetBookListByClassifyId(1);
    }

}
