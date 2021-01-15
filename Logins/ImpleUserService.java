package Logins;

import java.util.List;

public interface ImpleUserService {
	boolean insertUser(User u);  // 注册用户
	boolean updatePassword(String nickName, String password);  // 修改用户密码
	boolean updateScore(String nickName, int score);  // 修改用户得分
	List<User> selectAll();  // 查询所有用户
	User selectUserByNick(String nickName); // 根据账号查询用户
	User selectUser(String nickName, String password);     // 登录验证

}
