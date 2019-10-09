package wyb.example.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wyb.example.springboot.entity.*;
import wyb.example.springboot.mapper.BookMapper;
import wyb.example.springboot.mapper.BookPopularityMapper;
import wyb.example.springboot.service.BookService;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * @author William Wang
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookPopularityMapper bookPopularityMapper;

    @Override
    public List<AdministratorVO> getAdministratorVO(){
        List<BookDO> books = bookMapper.findAllBook();
        List<AdministratorVO> administratorVOs = new ArrayList<>();
        for(BookDO bookDO:books){
            AdministratorVO administratorVO = new AdministratorVO();
            //System.out.println(bookDO.getBookName());
            administratorVO.setAuthor(bookDO.getAuthor());
            administratorVO.setBookName(bookDO.getBookName());
            administratorVO.setBookId(bookDO.getBookId());
            administratorVO.setClassification(bookDO.getClassification());
            administratorVO.setIntroduction(bookDO.getIntroduction());
            administratorVO.setBookPopularity(bookPopularityMapper.getBookPopularityByBookName(bookDO.getBookName()));
            administratorVO.setBookScore(bookPopularityMapper.getBookScoreByBookName(bookDO.getBookName()));
            administratorVOs.add(administratorVO);
        }
        return administratorVOs;
    }
    @Override
    public void addBook(BookDO bookDO){
        bookMapper.addBook(bookDO);
        Float score = bookPopularityMapper.getBookScoreByBookName(bookDO.getBookName());
        if(score == null){
            bookPopularityMapper.addBook(bookDO.getBookName());
        }
    }
    @Override
    public void deleteBook(long bookId){
        bookMapper.deleteBook(bookId);
    }
    @Override
    public List<StudentBookSearchedVO> searchBook(String bookInfo,String infoType){
        String bookName = "bookName";
        if(infoType.equals(bookName)) {
            List<BookDO> books = bookMapper.findBookLikeBookName(bookInfo);
            List<StudentBookSearchedVO> studentBookSearchedVOs = new ArrayList<>();
            for(BookDO bookDO:books){
                StudentBookSearchedVO studentBookSearchedVO = new StudentBookSearchedVO();
                //System.out.println(bookDO.getBookName());
                studentBookSearchedVO.setAuthor(bookDO.getAuthor());
                studentBookSearchedVO.setBookName(bookDO.getBookName());
                studentBookSearchedVO.setBookId(bookDO.getBookId());
                studentBookSearchedVO.setClassification(bookDO.getClassification());
                studentBookSearchedVO.setIntroduction(bookDO.getIntroduction());
                studentBookSearchedVO.setBookId(bookDO.getBookId());
                studentBookSearchedVO.setBookPopularity(bookPopularityMapper.getBookPopularityByBookName(bookDO.getBookName()));
                studentBookSearchedVO.setBookScore(bookPopularityMapper.getBookScoreByBookName(bookDO.getBookName()));
                studentBookSearchedVOs.add(studentBookSearchedVO);
            }
            return studentBookSearchedVOs;
        } else{
            List<BookDO> books = bookMapper.findBookLikeAuthor(bookInfo);
            List<StudentBookSearchedVO> studentBookSearchedVOs = new ArrayList<>();
            for(BookDO bookDO:books){
                StudentBookSearchedVO studentBookSearchedVO = new StudentBookSearchedVO();
                //System.out.println(bookDO.getBookName());
                studentBookSearchedVO.setAuthor(bookDO.getAuthor());
                studentBookSearchedVO.setBookName(bookDO.getBookName());
                studentBookSearchedVO.setBookId(bookDO.getBookId());
                studentBookSearchedVO.setClassification(bookDO.getClassification());
                studentBookSearchedVO.setIntroduction(bookDO.getIntroduction());
                studentBookSearchedVO.setBookId(bookDO.getBookId());
                studentBookSearchedVO.setBookPopularity(bookPopularityMapper.getBookPopularityByBookName(bookDO.getBookName()));
                studentBookSearchedVO.setBookScore(bookPopularityMapper.getBookScoreByBookName(bookDO.getBookName()));
                studentBookSearchedVOs.add(studentBookSearchedVO);
            }
            return studentBookSearchedVOs;
        }
    }
    @Override
    public BookStateVO getBookById(long bookId){
        BookDO bookDO =  bookMapper.getBookByBookId(bookId);
        BookStateVO bookStateVO = new BookStateVO();
        bookStateVO.setAuthor(bookDO.getAuthor());
        bookStateVO.setBookName(bookDO.getBookName());
        bookStateVO.setBookState(bookDO.getBookState());
        bookStateVO.setBookId(bookDO.getBookId());
        bookStateVO.setClassification(bookDO.getClassification());
        bookStateVO.setIntroduction(bookDO.getIntroduction());
        bookStateVO.setBookId(bookDO.getBookId());
        bookStateVO.setBookPopularity(bookPopularityMapper.getBookPopularityByBookName(bookDO.getBookName()));
        bookStateVO.setBookScore(bookPopularityMapper.getBookScoreByBookName(bookDO.getBookName()));
        return bookStateVO;
    }
    @Override
    public List<BookPopularityLeaderBoardVO> getTopBooksByHeat(){
         List<BookPopularityDO> bpDOs = bookPopularityMapper.getTopBooksByPopularity();
         List<BookPopularityLeaderBoardVO> bplbVOs = new ArrayList<>();
         for(BookPopularityDO bpDO:bpDOs) {
             BookPopularityLeaderBoardVO bplbVO = new BookPopularityLeaderBoardVO();
             bplbVO.setAuthor(bookMapper.getAuthorByBookName(bpDO.getBookName()));
             bplbVO.setBookName(bpDO.getBookName());
             bplbVO.setBookPopularity(bpDO.getBookPopularity());
             bplbVO.setBookScore(bpDO.getBookScore());
             bplbVO.setClassification(bookMapper.getClassificationByBookName(bpDO.getBookName()));
             bplbVO.setIntroduction(bookMapper.getIntroductionByBookName(bpDO.getBookName()));
             bplbVOs.add(bplbVO);
         }
         return bplbVOs;
    }
}
