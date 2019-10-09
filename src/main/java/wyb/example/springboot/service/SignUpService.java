package wyb.example.springboot.service;

import wyb.example.springboot.entity.UserDO;

/**
 * @author William Wang
 */
public interface SignUpService {
    String signUp(UserDO userDO);
    String checkUserName(String userName);
    String CheckAdministratorName(String userName);
    String setAdministrator(String userName);
}
