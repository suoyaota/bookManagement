package wyb.example.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wyb.example.springboot.entity.*;
import wyb.example.springboot.service.BookService;
import wyb.example.springboot.service.BorrowingRecordService;
import wyb.example.springboot.service.SignInService;

import javax.servlet.http.HttpSession;
import java.awt.print.Book;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author William Wang
 */
@Controller
public class bookController {

    @Autowired
    SignInService signInService;
    @Autowired
    BookService bookService;
    @Autowired
    BorrowingRecordService borrowingRecordService;

    @RequestMapping("/addBook")
    public String addBook(
            @RequestParam("bookName")String bookName,
            @RequestParam("author")String author,
            @RequestParam("introduction")String introduction,
            @RequestParam("classification")String classification
            , HttpSession session){
        BookDO bookDO = new BookDO();
        String bookName1 = "《" + bookName +"》";
        bookDO.setBookName(bookName1);
        bookDO.setAuthor(author);
        bookDO.setIntroduction(introduction);
        bookDO.setClassification(classification);
        bookService.addBook(bookDO);
        String loginUserName = (String) session.getAttribute("loginUserName");
        long userId = signInService.getUserIdByUserName(loginUserName);
        return "redirect:administrator?userId="+userId;
    }
    @RequestMapping("/deleteBook")
    public String deleteBook(long bookId, HttpSession session){
        bookService.deleteBook(bookId);
        String loginUserName = (String) session.getAttribute("loginUserName");
        long userId = signInService.getUserIdByUserName(loginUserName);
        return "redirect:administrator?userId="+userId;
    }
    @GetMapping("/searchBook")
    public String searchBook(
            @RequestParam("bookInfo")String bookInfo,
            @RequestParam("infoType")String infoType,
            HttpSession session,
            Model m){
        List<StudentBookSearchedVO> studentBookSearchedVOs= bookService.searchBook(bookInfo,infoType);
        String userName = (String) session.getAttribute("loginUserName");
        long userId = signInService.getUserIdByUserName(userName);
        String Name = signInService.getNameByUserId(userId);
        List<StudentMyBookVO> studentMyBookVOs = borrowingRecordService.getMyBooks(userId);
        List<AdministratorVO> administratorVOs = bookService.getAdministratorVO();

        m.addAttribute("studentMyBookVOs",studentMyBookVOs);
        m.addAttribute("Name",Name);
        m.addAttribute("studentBookSearchedVOs",studentBookSearchedVOs);
        m.addAttribute("administratorVOs",administratorVOs);
        return "student";
    }
    @GetMapping("/bookState")
    public String bookState(long bookId,Model m,HttpSession session){
        String userName = (String) session.getAttribute("loginUserName");
        long userId = signInService.getUserIdByUserName(userName);
        BookStateVO bookStateVO = bookService.getBookById(bookId);
        m.addAttribute("book",bookStateVO);
        m.addAttribute("userId",userId);
        return "bookState";
    }
    @ResponseBody
    @PostMapping("/borrowBook")
    public String borrowBook(@RequestParam("bookId") long bookId,@RequestParam("userId") long userId){
        BookStateVO bookStateVO = bookService.getBookById(bookId);
        return borrowingRecordService.borrowBook(bookId,userId,bookStateVO);
    }
    @RequestMapping("/returnBook")
    public String returnBook(@RequestParam("bookId") long bookId,HttpSession session,Model m){
        String userName = (String) session.getAttribute("loginUserName");
        long userId = signInService.getUserIdByUserName(userName);
        borrowingRecordService.returnBook(bookId,userId);
        List<AdministratorVO> administratorVOs = bookService.getAdministratorVO();
        String Name = signInService.getNameByUserId(userId);
        List<StudentMyBookVO> studentMyBookVOs = borrowingRecordService.getMyBooks(userId);
        List<StudentBookSearchedVO> studentBookSearchedVOs = null;
        m.addAttribute("studentMyBookVOs",studentMyBookVOs);
        m.addAttribute("studentBookSearchedVOs",studentBookSearchedVOs);
        m.addAttribute("administratorVOs",administratorVOs);
        m.addAttribute("Name",Name);
        return "student";
    }
    @GetMapping("borrowingRecord")
    public String borrowingRecord(HttpSession session, Model m){
        java.util.Date today = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String todayFormat = ft.format(today);
        String userName = (String) session.getAttribute("loginUserName");
        long userId = signInService.getUserIdByUserName(userName);
        String name = signInService.getNameByUserId(userId);
        List<BorrowingRecordVO> brVOs = borrowingRecordService.getBorrowingRecordVOByUserId(userId);
//        List<Book> borrowedBooks = new ArrayList<>();
//        for(BorrowingRecord brr: br){
//            borrowedBooks.add(bookService.getBookByID(brr.getBookID()));
//        }
//        m.addAttribute("borrowedBooks",borrowedBooks);
        String deadlineHint = borrowingRecordService.getDeadlineHint(userId);
        m.addAttribute("hint",deadlineHint);
        m.addAttribute("today",todayFormat);
        m.addAttribute("brVOs",brVOs);
        m.addAttribute("name",name);
        return "borrowingRecords";
    }
    @PostMapping("postScore")
    public String postScore(@RequestParam("score")int score,HttpSession session){
        long brId = (long) session.getAttribute("brId");
        borrowingRecordService.postScore(brId,score);
        return "redirect:borrowingRecord";
        //redirect导向控制器
    }

    @GetMapping("goToPostScore")
    public String GoToPostScore(@RequestParam("brId")long brId,HttpSession session,Model m){
        String bookName = borrowingRecordService.getBookNameByBrId(brId);
        String userName = (String) session.getAttribute("loginUserName");
        long userId = signInService.getUserIdByUserName(userName);
        String name = signInService.getNameByUserId(userId);
        m.addAttribute("name",name);
        m.addAttribute("bookName",bookName);
        session.setAttribute("brId",brId);
        return "score";
    }
    @GetMapping("updateHeat")
    public String updateHeat(HttpSession session){
        String userName = (String) session.getAttribute("loginUserName");
        long userId = signInService.getUserIdByUserName(userName);
        borrowingRecordService.updateHeat();
        return "redirect:/administrator?userId="+userId;
    }
    @GetMapping("BookPopularityLeaderBoard")
    public String heatLeaderBoard(Model m){
        List<BookPopularityLeaderBoardVO> bplbVOs = bookService.getTopBooksByHeat();
        m.addAttribute("bplbVOs",bplbVOs);
        return "BookPopularityLeaderBoard";
    }


}

