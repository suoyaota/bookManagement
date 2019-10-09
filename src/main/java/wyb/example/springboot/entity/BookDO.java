package wyb.example.springboot.entity;

import lombok.Data;

/**
 * @author William Wang
 */

@Data
public class BookDO {
    private Long bookId;
    private String bookName;
    private String author;
    private String introduction;
    private String classification;
    private Integer bookState;
}
