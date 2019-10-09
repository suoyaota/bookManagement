package wyb.example.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import wyb.example.springboot.entity.BookDO;
import wyb.example.springboot.entity.BookPopularityDO;

import java.awt.*;
import java.awt.print.Book;
import java.util.List;

/**
 * @author William Wang
 */
@Component
public interface BookPopularityMapper {
    @Insert("INSERT INTO bookPopularity (bookName) values (#{bookName})")
    void addBook(String bookName);
    @Select("SELECT bookPopularity FROM bookPopularity WHERE bookName = #{bookName}")
    Float getBookPopularityByBookName (@Param("bookName") String bookName);
    @Select("SELECT bookScore FROM bookPopularity WHERE bookName = #{bookName}")
    Float getBookScoreByBookName (@Param("bookName") String bookName);
    @Select("SELECT * FROM bookPopularity WHERE bookName = #{bookName}")
    BookPopularityDO getBookPopularityDOByBookName (@Param("bookName") String bookName);
    @Update("UPDATE bookPopularity SET scoreNum = scoreNum+1 WHERE bookName = #{bookName}")
    void addScoreNumByBookName(String bookName);
    @Update("UPDATE bookPopularity SET bookScore = #{bookScore} WHERE bookName = #{bookName}")
    void updateBookScoreByBookName(@Param("bookScore") float bookScore,@Param("bookName") String bookName);
    @Update("UPDATE bookPopularity SET bookPopularity = #{bookPopularity} WHERE bookName = #{bookName}")
    void updatebookPopularityByBookName(@Param("bookPopularity") Double bookPopularity,@Param("bookName") String bookName);
    @Select("SELECT * FROM bookPopularity ORDER BY bookPopularity DESC LIMIT 0,10")
    List<BookPopularityDO> getTopBooksByPopularity();
//
//    @Insert("INSERT INTO borrowingRecord (userID,bookID,borrowingDate,bookName,author,result) " +
//            "VALUES (#{userID},#{bookID},#{borrowingDate},#{bookName},#{author},#{result})")
//    void addBorrowingRecord(BorrowingRecord borrowingRecord);
//
//    @Select("SELECT * FROM borrowingRecord WHERE userID = #{userID}")
//    List<BorrowingRecord> getBorrowingRecordsByUserID(long userID);
//
//    @Update("Update borrowingRecord SET returningDate=#{returningDate},result = 1" +
//            " where userID = #{userID} AND bookID = #{bookID}")
//    void returnBook(@Param("returningDate") java.sql.Date returningDate,
//                    @Param("userID") long userID, @Param("bookID") long bookID);
//    @Update("UPDATE borrowingRecord SET score=#{score} WHERE borrowingID=#{brID}")
//    void postScore(@Param("brID")long brID,@Param("score")int score);
}
