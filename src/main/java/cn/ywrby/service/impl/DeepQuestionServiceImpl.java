package cn.ywrby.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ywrby.domain.AnswerForm;
import cn.ywrby.domain.QuestionForm;
import cn.ywrby.mapper.DeepQuestionMapper;
import cn.ywrby.service.DeepQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeepQuestionServiceImpl implements DeepQuestionService {

    @Autowired
    DeepQuestionMapper dqMapper;


    @Override
    public List<QuestionForm> getQuestionFormByBookId(String bookId) {
        List<QuestionForm> questionForms=dqMapper.getQuestionFormByBookId(bookId);
        return questionForms;
    }

    @Override
    public AnswerForm getAnswerForm(String answerFormStr) {
        AnswerForm answerForm=new AnswerForm();
        JSONObject jsonObject= JSONUtil.parseObj(answerFormStr);
        int bookId= (int) jsonObject.get("bookId");
        int questionId= (int) jsonObject.get("questionId");
        String answer= (String) jsonObject.get("answer");
        int rate= (int) jsonObject.get("rate");
        String opinion= (String) jsonObject.get("opinion");
        answerForm.setBookId(bookId);
        answerForm.setQuestionId(questionId);
        answerForm.setAnswer(answer);
        answerForm.setRate(rate);
        answerForm.setOpinion(opinion);
        return answerForm;
    }

    @Override
    public boolean insertAnswerForm(AnswerForm answerForm) {
        int answerId= dqMapper.insertAnswerForm(answerForm);
        System.out.println(answerId);
        if(answerId!=0){
            return true;
        }else {
            return false;
        }
    }
}
