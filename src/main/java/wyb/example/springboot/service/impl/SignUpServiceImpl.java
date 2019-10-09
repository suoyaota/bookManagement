package wyb.example.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wyb.example.springboot.entity.UserDO;
import wyb.example.springboot.mapper.UserMapper;
import wyb.example.springboot.service.SignUpService;

import java.util.Objects;

/**
 * @author William Wang
 */
@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    UserMapper userMapper;

    @Override
    public String signUp(UserDO userDO){
        userDO.setPassword(BCrypt.hashpw(userDO.getPassword(),BCrypt.gensalt()));
        userMapper.signUp(userDO);
        return "1";
    }

    @Override
    public String checkUserName(String userName){
        Long id = userMapper.getUserIdByUserName(userName);
        if(Objects.isNull(id)){
            //return "<font color='green'>可以使用</font>";
            return "1";
        }else {
            return "0";
        }
    }

    @Override
    public String CheckAdministratorName(String userName){
        Integer role = userMapper.getRoleByUserName(userName);
        if(Objects.isNull(role)){
            return "1";
        }else if(role == 1){
            return "2";
        }else{
            return userName;
        }
    }
    @Override
    public String setAdministrator(String userName){
        userMapper.setAdministrator(userName);
        return "1";
    }
}
