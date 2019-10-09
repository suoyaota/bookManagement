package wyb.example.springboot.entity;

import lombok.Data;
/**
 * @author William Wang
 */
@Data
public class UserDO {
    private Long userId;
    private String userName;
    private String password;
    private String name;
    private Integer isAdministrator;
    private Long brNum;
}
