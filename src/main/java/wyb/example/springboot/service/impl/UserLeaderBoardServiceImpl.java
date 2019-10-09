package wyb.example.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wyb.example.springboot.entity.UserLeaderBoardVO;
import wyb.example.springboot.mapper.UserMapper;
import wyb.example.springboot.service.UserLeaderBoardService;

import java.util.List;

/**
 * @author William Wang
 */
@Service
public class UserLeaderBoardServiceImpl implements UserLeaderBoardService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserLeaderBoardVO> getTopUsers(){
        return userMapper.getTopUsers();
    }
}
