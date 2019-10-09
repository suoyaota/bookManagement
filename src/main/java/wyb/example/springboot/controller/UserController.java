package wyb.example.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wyb.example.springboot.entity.*;
import wyb.example.springboot.service.*;

import javax.servlet.http.HttpSession;
import java.awt.print.Book;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    SignInService signInService;
    @Autowired
    SignUpService signUpService;
    @Autowired
    BookService bookService;
    @Autowired
    BorrowingRecordService borrowingRecordService;
    @Autowired
    UserLeaderBoardService userLeaderBoardService;

    @GetMapping(value = "/")
    public String signIn() {
        return "signIn";
    }

    @GetMapping(value = "/SignInSuccess")
    public String test() {
        return "SignSuccess";
    }

    //@RequestMapping (value = "/login" ,method = RequestMethod.POST)
    @ResponseBody  //有这个注解就是返回信息，否则就是跳转页面 20190729
    @PostMapping(value = "/signIn")
    public String loginCon(UserDO userDO, HttpSession session) {
        //public String loginCon(@RequestParam("userName") String userName, @RequestParam("password") String password)
        //上面写法也可
        //public String loginCon(@RequestBody User user)
        // 上面写法报错 HttpMessageNotReadableException: Required request body is missing:
        //20190729
        String returnMsg = signInService.signIn(userDO,session);
        return returnMsg;
        //m.addAttribute(user1);
    }
    @GetMapping(value = "/main")
    public String mainTest(){
        return "main";
    }
    @GetMapping(value = "/toSignIn")
    public String toSignIn(){
        return "toSignIn";
    }
    @GetMapping(value = "/toSignUp")
    public String toSignUp(){
        return "signUp";
    }
    @ResponseBody
    @PostMapping(value = "/signUp")
    public String signUp(UserDO userDO){
        return signUpService.signUp(userDO);
    }
    @ResponseBody
    @GetMapping("/userName/check")
    public String checkUserName(String userName){
        return signUpService.checkUserName(userName);
    }
    @GetMapping("/bookManagement")
    public String LoginSuccess(@RequestParam("userId") long userId){
        int role = signInService.getRoleByUserId(userId);

        if(role == 1){
            //跳转到管理员界面
            return "redirect:administrator?userId="+userId;
        }else {
            //跳转到学生页面
            return "redirect:student?userId="+userId;
        }
    }
    @GetMapping("/administrator")
    public String toAdministrator(@RequestParam("userId") long userId, Model m){
        List<AdministratorVO> administratorVOs = bookService.getAdministratorVO();
        String Name = signInService.getNameByUserId(userId);
        m.addAttribute("administratorVOs",administratorVOs);
        m.addAttribute("Name123",Name);
        return "administrator";
    }
    @GetMapping("/student")
    public String toStudent(@RequestParam("userId") long userId, Model m){
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
    @ResponseBody
    @GetMapping("/checkAdministratorName")
    public String CheckAdministratorName(String userName){
        return signUpService.CheckAdministratorName(userName);
    }
    @ResponseBody
    @PostMapping("/setAdministrator")
    public String setAdministrator(String userName){
        return signUpService.setAdministrator(userName);
    }

    @GetMapping("userLeaderBoard")
    public String userLeaderBoard(Model m){
        List<UserLeaderBoardVO> topUsers =  userLeaderBoardService.getTopUsers();
        m.addAttribute("topUsers",topUsers);
        return "userLeaderBoard";
    }


}