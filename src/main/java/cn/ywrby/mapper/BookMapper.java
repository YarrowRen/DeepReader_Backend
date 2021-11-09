package cn.ywrby.mapper;

import cn.ywrby.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    public List<Book> getBookList();

    public List<Book> getBookListByUsername(@Param("username")String username);

    public List<Book> getSimplifyBookListByClassifyId(@Param("classifyId")int classifyId);

    public String getBookContent(@Param("bookId")int bookId);
}
