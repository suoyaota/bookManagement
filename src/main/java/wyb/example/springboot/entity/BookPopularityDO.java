package wyb.example.springboot.entity;

import lombok.Data;
/**
 * @author William Wang
 */
@Data
public class BookPopularityDO {
    private String bookName;
    private Float bookPopularity;
    private Float bookScore;
    private Integer scoreNum;
}
