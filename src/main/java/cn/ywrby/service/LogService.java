package cn.ywrby.service;

import cn.ywrby.domain.Log;

import java.util.Map;

public interface LogService {
    public Log getUserBookLog(int userId,int bookId);

    void viewBook(int userId, int bookId);

    void viewBookAtExam(int userId, int bookId);

    Map<String,Double> userDataCount(int userId);
}
