package wyb.example.springboot.service;

import wyb.example.springboot.entity.UserLeaderBoardVO;

import java.util.List;

/**
 * @author William Wang
 */
public interface UserLeaderBoardService {
    List<UserLeaderBoardVO> getTopUsers();
}
