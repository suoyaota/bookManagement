package wyb.example.springboot.entity;

import lombok.Data;

import java.sql.Date;
/**
 * @author William Wang
 */
@Data
public class BorrowingRecordDO {
    private Long borrowingId;
    private Long userId;
    private Long bookId;
    private java.sql.Date borrowingDate;
    private java.sql.Date returningDate;
    private Integer result;
    private Integer score;
}
