package wyb.example.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wyb.example.springboot.entity.*;
import wyb.example.springboot.mapper.BookMapper;
import wyb.example.springboot.mapper.BookPopularityMapper;
import wyb.example.springboot.mapper.BorrowingRecordMapper;
import wyb.example.springboot.mapper.UserMapper;
import wyb.example.springboot.service.BorrowingRecordService;

import java.awt.print.Book;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author William Wang
 */
@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {
    /**
     * readTime是可以阅读的天数
     */
    public static int readTime = 6;
    @Autowired
    BorrowingRecordMapper borrowingRecordMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookPopularityMapper bookPopularityMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public List<StudentMyBookVO> getMyBooks(long userId){
        List<BorrowingRecordDO> myBR = borrowingRecordMapper.getBorrowingRecordsByUserId(userId);
        List<Long> myBooksId = new ArrayList<>();
        for(BorrowingRecordDO br:myBR){
            if(br.getResult() == 0){
                myBooksId.add(br.getBookId());
            }
        }
        List<BookDO> myBooks = new ArrayList<>();
        for(Long bookId:myBooksId){
            BookDO book = bookMapper.getBookByBookId(bookId);
            if(book.getBookState() == 0) {
                myBooks.add(book);
            }
        }
        List<StudentMyBookVO> studentMyBookVOs = new ArrayList<>();
        for(BookDO bookDO:myBooks){
            StudentMyBookVO studentMyBookVO = new StudentMyBookVO();
            //System.out.println(bookDO.getBookName());
            studentMyBookVO.setAuthor(bookDO.getAuthor());
            studentMyBookVO.setBookName(bookDO.getBookName());
            studentMyBookVO.setBookId(bookDO.getBookId());
            studentMyBookVO.setClassification(bookDO.getClassification());
            studentMyBookVO.setIntroduction(bookDO.getIntroduction());
            studentMyBookVO.setBookId(bookDO.getBookId());
            studentMyBookVO.setBookPopularity(bookPopularityMapper.getBookPopularityByBookName(bookDO.getBookName()));
            studentMyBookVO.setBookScore(bookPopularityMapper.getBookScoreByBookName(bookDO.getBookName()));
            studentMyBookVOs.add(studentMyBookVO);
        }
        return studentMyBookVOs;
    }
    @Override
    public String borrowBook(long bookId, long userId, BookStateVO bookStateVO){
        BorrowingRecordDO br = new BorrowingRecordDO();
        br.setBookId(bookId);
        br.setUserId(userId);
        java.util.Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        br.setBorrowingDate(sqlDate);
        br.setResult(0);
        borrowingRecordMapper.addBorrowingRecord(br);
        bookMapper.setBookState0ByBookId(bookId);
        return "1";
    }
    @Override
    public void returnBook(long bookId,long userId){
        java.util.Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        userMapper.updateBrNumByUserId(userId);
        bookMapper.setBookState1ByBookId(bookId);
        borrowingRecordMapper.returnBook(sqlDate,userId,bookId);
    }
    @Override
    public List<BorrowingRecordVO> getBorrowingRecordVOByUserId(long userId){
        List<BorrowingRecordDO> brDOs =borrowingRecordMapper.getBorrowingRecordsByUserId(userId);
        List<BorrowingRecordVO> brVOs = new ArrayList<>();


        for(BorrowingRecordDO brDO: brDOs){
            BorrowingRecordVO brVO = new BorrowingRecordVO();
            brVO.setBorrowingDate(brDO.getBorrowingDate());
            brVO.setBorrowingId(brDO.getBorrowingId());
            brVO.setResult(brDO.getResult());
            brVO.setScore(brDO.getScore());
            brVO.setAuthor(bookMapper.getAuthorByBookId(brDO.getBookId()));
            brVO.setBookName(bookMapper.getBookNameByBookId(brDO.getBookId()));
            brVO.setReturningDate(brDO.getReturningDate());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(brDO.getBorrowingDate());
            rightNow.add(Calendar.DAY_OF_YEAR,readTime);
            Date ddl = rightNow.getTime();
            brVO.setDeadline(sdf.format(ddl));
            brVOs.add(brVO);
        }
        return brVOs;
    }
    @Override
    public String getDeadlineHint(long userId){
        List<BorrowingRecordDO> brDOs =borrowingRecordMapper.getBorrowingRecordsByUserId(userId);
        java.util.Date today = new Date();
        String ans = "";
        for(BorrowingRecordDO brDO: brDOs){
            if(brDO.getResult() == 0) {
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(brDO.getBorrowingDate());
                rightNow.add(Calendar.DAY_OF_YEAR, readTime);
                java.util.Date ddl = rightNow.getTime();
                long betweenDate = (ddl.getTime() - today.getTime()) / (60 * 60 * 24 * 1000);
                if (betweenDate < 0) {
                    ans = ans + bookMapper.getBookNameByBookId(brDO.getBookId()) + "已经过期！" + "\n";
                } else {
                    ans = ans + bookMapper.getBookNameByBookId(brDO.getBookId())  + "还有 " + betweenDate + " 天到期！" + "\n";
                }
            }
        }
        return ans;
    }
    @Override
    public String getBookNameByBrId(long brId) {
        long bookId = borrowingRecordMapper.getBookIdByBrId(brId);
        String bookName = bookMapper.getBookNameByBookId(bookId);
        return bookName;
    }
    @Override
    public void postScore(long brId,int score){
        borrowingRecordMapper.postScore(brId,score);
        long bookId = borrowingRecordMapper.getBookIdByBrId(brId);
        String bookName = bookMapper.getBookNameByBookId(bookId);
        bookPopularityMapper.addScoreNumByBookName(bookName);
        BookPopularityDO bookPopularityDO = bookPopularityMapper.getBookPopularityDOByBookName(bookName);
        float bookScore = (bookPopularityDO.getBookScore()*(bookPopularityDO.getScoreNum()-1)+score)/bookPopularityDO.getScoreNum();
        bookPopularityMapper.updateBookScoreByBookName(bookScore,bookName);
    }

    @Override
    public void updateHeat(){

        List<BookDO> allBooks = bookMapper.findAllBook();
        List<BorrowingRecordDO> brDOs = borrowingRecordMapper.getBorrowingRecordForHeat();

//        for(Book b: allBooks){
//            System.out.println(b.getBookName());
//        }
//       for(BorrowingRecord brr: br){
//          System.out.println(brr.getBookName());
//       }

        for(BookDO bookDO:allBooks){
            long diff = 0;
            java.util.Date today = new Date();
            for(BorrowingRecordDO brDO: brDOs){
                if(bookDO.getBookId().equals(brDO.getBookId())){
                    java.sql.Date sqlDate = brDO.getBorrowingDate();
                    java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                    diff += (utilDate.getTime()-(today.getTime()-10*24 * 60 * 60 * 1000))/ (24 * 60 * 60 * 1000);
                }
            }
            //该bookId出现日期-十天前日期的累加 *2.33 算出热度 最大值100
            Double heat = diff*2.33;
            if (heat>100) {
                heat = 100.0;
            }
            bookPopularityMapper.updatebookPopularityByBookName(heat,bookDO.getBookName());
        }
    }
}
