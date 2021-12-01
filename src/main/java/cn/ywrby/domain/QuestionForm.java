package cn.ywrby.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionForm {
    private int id;
    private String question;
    private String type;
    private String answer;
    private String clues;
    private String robotQuestion;
    private int rate;
    private String modifyQuestion;
    private int userId;
    private int bookId;

}
