package wyb.example.springboot.service;

import wyb.example.springboot.entity.*;

import java.awt.print.Book;
import java.util.List;

/**
 * @author William Wang
 */

public interface BookService {
    List<AdministratorVO> getAdministratorVO();
    void addBook(BookDO bookDO);
    void deleteBook(long bookId);
    List<StudentBookSearchedVO> searchBook(String bookInfo, String infoType);
    BookStateVO getBookById(long bookId);
    List<BookPopularityLeaderBoardVO> getTopBooksByHeat();
}
