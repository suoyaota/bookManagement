package wyb.example.springboot.entity;

import lombok.Data;

/**
 * @author William Wang
 */

@Data
public class StudentMyBookVO {
    private Long bookId;
    private String bookName;
    private String author;
    private String introduction;
    private String classification;
    private Float bookScore;
    private Float bookPopularity;
}
