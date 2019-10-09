package wyb.example.springboot.mapper;



import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import wyb.example.springboot.entity.UserDO;
import wyb.example.springboot.entity.UserLeaderBoardVO;

import java.util.List;

@Component
public interface UserMapper {
    @Update("UPDATE user SET brNum = brNum+1 WHERE userId = #{userId}")
    void updateBrNumByUserId(long userId);
    @Select("SELECT name,brNum FROM user ORDER BY brNum DESC LIMIT 0,10")
    List<UserLeaderBoardVO> getTopUsers();
    @Select("select password from user where userName=#{userName}")
    String getPasswordByUserName(@Param("userName") String userName);
//    @Select("select * from user where userID=#{userID}")
//    User getUserByID(@Param("userID")long id);
    @Select("select isAdministrator from user where userId=#{userId}")
    Integer getRoleByUserId(@Param("userId")long id);
    @Select("select isAdministrator from user where userName=#{userName}")
    Integer getRoleByUserName(@Param("userName")String userName);
    @Select("select name from user where userId=#{userId}")
    String getNameByUserId(@Param("userId")long id);
    @Select("select userId from user where userName = #{userName}")
    Long getUserIdByUserName(@Param("userName")String userName);
    @Insert("insert into user ( userName, password, name) values( #{userName}, #{password}, #{name})")
    void signUp(UserDO userDO);
    @Update("update user set isAdministrator = 1 where userName = #{userName}")
    void setAdministrator(String userName);
}
