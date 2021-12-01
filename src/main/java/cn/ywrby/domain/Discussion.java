package cn.ywrby.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Discussion {
    private int id;
    private int userId;
    private String userName;
    private int classifyId;
    private String classifyName;
    private Date createTime;
    private String topic;
    private String content;
}
