package wyb.example.springboot.service;

import wyb.example.springboot.entity.BookDO;
import wyb.example.springboot.entity.BookStateVO;
import wyb.example.springboot.entity.BorrowingRecordVO;
import wyb.example.springboot.entity.StudentMyBookVO;

import java.awt.print.Book;
import java.util.List;

/**
 * @author William Wang
 */
public interface BorrowingRecordService {
    List<StudentMyBookVO> getMyBooks(long userId);
    String borrowBook(long bookId, long userId, BookStateVO bookStateVO);
    void returnBook(long bookId,long userId);
    List<BorrowingRecordVO> getBorrowingRecordVOByUserId(long userId);
    String getDeadlineHint(long userId);
    String getBookNameByBrId(long brId);
    void postScore(long brId,int score);
    void updateHeat();
}
