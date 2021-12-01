package cn.ywrby.service.impl;

import cn.ywrby.domain.Log;
import cn.ywrby.mapper.LogMapper;
import cn.ywrby.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    @Override
    public Log getUserBookLog(int userId, int bookId) {
        Log log = logMapper.getUserBookLog(userId, bookId);

        return log;
    }

    @Override
    public void viewBook(int userId, int bookId) {
        Log log = getUserBookLog(userId, bookId);
        if (log==null){
            log=new Log();
            log.setUserId(userId);
            log.setBookId(bookId);
            log.setBook_selection_click(1);
            logMapper.insertBookView(log);
        }else {
            log.setBook_selection_click(log.getBook_selection_click()+1);
            logMapper.updateBookView(log);
        }
    }

    @Override
    public void viewBookAtExam(int userId, int bookId) {
        Log log = getUserBookLog(userId, bookId);
        if (log==null){
            log=new Log();
            log.setUserId(userId);
            log.setBookId(bookId);
            log.setBook_exam_click(1);
            logMapper.insertBookView(log);
        }else {
            log.setBook_exam_click(log.getBook_exam_click()+1);
            logMapper.updateBookView(log);
        }
    }

    @Override
    public Map<String,Double> userDataCount(int userId) {
        Map<String,Double> dataCount=new HashMap<>();
        double readCount=logMapper.userReadCount(userId);
        double questionCount=logMapper.userQuestionCount(userId);
        double answerCount=logMapper.userAnswerCount(userId);
        double bookCount=logMapper.userBookCount(userId);
        double unReadCount=bookCount-readCount;
        double opinionCount=logMapper.userOpinionCount(userId);
        double avgReadTime=logMapper.userAvgReadTime(userId);
        double avgQuestionRate=logMapper.userAvgQuestionRate(userId);
        double beAnsweredCount=logMapper.userBeAnsweredCount(userId);

        dataCount.put("readCount",readCount);
        dataCount.put("questionCount",questionCount);
        dataCount.put("answerCount",answerCount);
        dataCount.put("unReadCount",unReadCount);
        dataCount.put("opinionCount",opinionCount);
        dataCount.put("avgReadTime",avgReadTime);
        dataCount.put("avgQuestionRate",avgQuestionRate);
        dataCount.put("beAnsweredCount",beAnsweredCount);
        return dataCount;
    }
}
