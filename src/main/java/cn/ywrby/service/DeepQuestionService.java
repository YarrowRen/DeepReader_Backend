package cn.ywrby.service;

import cn.ywrby.domain.AnswerForm;
import cn.ywrby.domain.QuestionForm;

import java.util.List;

public interface DeepQuestionService {
    List<QuestionForm> getQuestionFormByBookId(String bookId);

    AnswerForm getAnswerForm(String answerFormStr);

    boolean insertAnswerForm(AnswerForm answerForm);
}
