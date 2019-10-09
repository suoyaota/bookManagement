package wyb.example.springboot.entity;

import lombok.Data;

/**
 * @author William Wang
 */

@Data
public class BookPopularityLeaderBoardVO {
    private Float bookPopularity;
    private Float bookScore;
    private String bookName;
    private String author;
    private String introduction;
    private String classification;
}
