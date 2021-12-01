package cn.ywrby.domain;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KWL {
    private int id;
    private int userId;
    private int bookId;
    private String preKnown;
    private String QA;
    private int rate;
    private String summary;
    private int read_time;
}
