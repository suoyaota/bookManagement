package wyb.example.springboot.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import wyb.example.springboot.entity.BorrowingRecordDO;

import java.util.List;

@Component
public interface BorrowingRecordMapper {

    @Select("SELECT * FROM borrowingRecord where DATE_SUB(CURDATE(), INTERVAL 10 DAY) <= date(borrowingDate)")
    List<BorrowingRecordDO> getBorrowingRecordForHeat();
//
//    @Select("SELECT * FROM borrowingRecord WHERE borrowingID = #{brID}")
//    BorrowingRecord getBorrowingRecordByBrID (@Param("brID") long brID);
//
    @Insert("INSERT INTO borrowingRecord (userId,bookId,borrowingDate,result) " +
            "VALUES (#{userId},#{bookId},#{borrowingDate},#{result})")
    void addBorrowingRecord(BorrowingRecordDO borrowingRecordDO);
//
    @Select("SELECT * FROM borrowingRecord WHERE userId = #{userId}")
    List<BorrowingRecordDO> getBorrowingRecordsByUserId(long userId);
    @Select("SELECT bookId FROM borrowingRecord WHERE borrowingId = #{brId}")
    Long getBookIdByBrId(long brId);

    @Update("Update borrowingRecord SET returningDate=#{returningDate},result = 1" +
            " where userId = #{userId} AND bookId = #{bookId}")
    void returnBook(@Param("returningDate") java.sql.Date returningDate,
                    @Param("userId") long userId, @Param("bookId") long bookId);
    @Update("UPDATE borrowingRecord SET score=#{score} WHERE borrowingId=#{brId}")
    void postScore(@Param("brId")long brId,@Param("score")int score);
}

