package wyb.example.springboot.entity;

import lombok.Data;

/**
 * @author William Wang
 */

@Data
public class BookStateVO {
    private Long bookId;
    private String bookName;
    private String author;
    private String introduction;
    private String classification;
    private Integer bookState;
    private Float bookPopularity;
    private Float bookScore;
}
