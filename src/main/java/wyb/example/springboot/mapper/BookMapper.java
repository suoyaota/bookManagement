package wyb.example.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import wyb.example.springboot.entity.BookDO;

import java.awt.print.Book;
import java.util.List;

@Component
public interface BookMapper {

//    @Select("SELECT * FROM book ORDER BY heat DESC LIMIT 0,10")
//    List<Book> getTopBooksByHeat();
//    @Update("UPDATE book SET heat = #{heat} WHERE bookID = #{bookID}")
//    void updateHeatByBookID(@Param("heat")double heat,@Param("bookID")long bookID);
    @Insert("INSERT INTO book (bookName, author, introduction, classification) " +
            "values (#{bookName},#{author},#{introduction},#{classification})")
    void addBook(BookDO bookDO);
    @Select("SELECT * FROM book")
    List<BookDO> findAllBook();
    @Select("SELECT * FROM book WHERE bookName like concat('%',#{bookName},'%')")
    List<BookDO> findBookLikeBookName(@Param("bookName")String bookName);
    @Select("SELECT * FROM book WHERE author LIKE concat('%',#{author},'%')")
    List<BookDO> findBookLikeAuthor(@Param("author") String author);
    @Select("SELECT * FROM book WHERE bookId = #{bookId}")
    BookDO getBookByBookId(long bookId);
    @Select("SELECT author FROM book WHERE bookId = #{bookId}")
    String getAuthorByBookId(long bookId);
    @Select("SELECT bookName FROM book WHERE bookId = #{bookId}")
    String getBookNameByBookId(long bookId);
    @Select("SELECT author FROM book WHERE bookName = #{bookName} LIMIT 0,1")
    String getAuthorByBookName(String bookName);
    @Select("SELECT introduction FROM book WHERE bookName = #{bookName} LIMIT 0,1")
    String getIntroductionByBookName(String bookName);
    @Select("SELECT classification FROM book WHERE bookName = #{bookName} LIMIT 0,1")
    String getClassificationByBookName(String bookName);
    @Delete("DELETE FROM book where bookId = #{bookId}")
    void deleteBook(long bookId);
    @Update("UPDATE book SET bookState = 0 WHERE bookId = #{bookId}")
    void setBookState0ByBookId(long bookId);
    @Update("UPDATE book SET bookState = 1 WHERE bookId = #{bookId}")
    void setBookState1ByBookId(long bookId);

//    @Update("UPDATE book SET score = #{score} WHERE bookID = #{bookID}")
//    void updateBookScore(@Param("score")float score,@Param("bookID")long bookID);
}
