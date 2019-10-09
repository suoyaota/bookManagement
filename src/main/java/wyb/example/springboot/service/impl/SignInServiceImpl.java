package wyb.example.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wyb.example.springboot.entity.AdministratorVO;
import wyb.example.springboot.entity.UserDO;
import wyb.example.springboot.mapper.UserMapper;
import wyb.example.springboot.service.SignInService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author William Wang
 */
@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    UserMapper userMapper;

    @Override
    public String signIn(UserDO userDO, HttpSession session){

        String userName = userDO.getUserName();
        String password = userDO.getPassword();
        String truePassword = userMapper.getPasswordByUserName(userName);
        if(BCrypt.checkpw(password,truePassword)){
            Long loginUserId = userMapper.getUserIdByUserName(userName);
            session.setAttribute("loginUserName",userDO.getUserName());
            return loginUserId.toString();
        }else{
            //登录失败
            return "0";
        }
    }
    @Override
    public int getRoleByUserId(long userId){
        return userMapper.getRoleByUserId(userId);
    }
    @Override
    public String getNameByUserId(long userId){
        return userMapper.getNameByUserId(userId);
    }
    @Override
    public Long getUserIdByUserName(String userName) {
        return userMapper.getUserIdByUserName(userName);
    }


}
