package cn.ywrby.mapper;

import cn.ywrby.domain.AnswerForm;
import cn.ywrby.domain.QuestionForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeepQuestionMapper {
    public List<QuestionForm> getQuestionFormByBookId(String bookId);

    int insertAnswerForm(AnswerForm answerForm);
}
