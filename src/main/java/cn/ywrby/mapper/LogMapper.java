package cn.ywrby.mapper;

import cn.ywrby.domain.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    public Log getUserBookLog(int userId,int bookId);

    void insertBookView(Log log);

    void updateBookView(Log log);

    int userReadCount(int userId);

    int userQuestionCount(int userId);

    int userAnswerCount(int userId);

    int userBookCount(int userId);

    int userOpinionCount(int userId);

    int userAvgReadTime(int userId);

    double userAvgQuestionRate(int userId);

    int userBeAnsweredCount(int userId);
}
