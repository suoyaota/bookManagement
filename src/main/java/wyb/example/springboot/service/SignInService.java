package wyb.example.springboot.service;

import wyb.example.springboot.entity.AdministratorVO;
import wyb.example.springboot.entity.UserDO;

import javax.servlet.http.HttpSession;

/**
 * @author William Wang
 */


public interface SignInService {
     String signIn(UserDO userDO, HttpSession session);
     int getRoleByUserId(long userId);
     String getNameByUserId(long userId);
     Long getUserIdByUserName(String userName);
}
