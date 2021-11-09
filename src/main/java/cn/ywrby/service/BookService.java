package cn.ywrby.service;


import cn.ywrby.domain.Book;
import cn.ywrby.domain.Classify;

import java.util.List;

public interface BookService {
    public List<Book> getBookListByUsername(String username);

    public List<Classify> getClassifyAndBookList();

    public List<Book> getBookList(Integer page,Integer pageSize);

    public String getBookContent(Integer bookId);
}
