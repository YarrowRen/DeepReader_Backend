package cn.ywrby.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    private int id;
    private String name;
    private String author;
    private Timestamp start_date;
    private Timestamp end_date;
    private int pages;
    private String brief_introduction;
    private int reading_stage;

}
