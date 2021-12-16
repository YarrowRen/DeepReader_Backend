package cn.ywrby;


import cn.ywrby.domain.Heat;
import cn.ywrby.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookStoreDemo2ApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testForHeat(){
        List<Heat> userHeat = userMapper.getUserHeat(1);
        System.out.println(userHeat);
    }

}
