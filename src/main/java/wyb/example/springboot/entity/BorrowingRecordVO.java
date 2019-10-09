package wyb.example.springboot.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @author William Wang
 */

@Data
public class BorrowingRecordVO {

    private String bookName;
    private String author;
    private Long borrowingId;
    private Date borrowingDate;
    private Date returningDate;
    private String deadline;
    private Integer result;
    private Integer score;

}
