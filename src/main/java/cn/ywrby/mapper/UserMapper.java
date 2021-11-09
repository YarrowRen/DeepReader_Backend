package cn.ywrby.mapper;

import cn.ywrby.domain.User;
import cn.ywrby.domain.UserClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 获取所有用户的数据
     * @return 所有用户数据组成的集合
     */
    public List<User> findAll() ;

    /**
     * 通过用户的校园号与密码验证是否存在用户并返回，主要用于登录
     * @return
     */
    public User findUserByUserAndPwd(@Param("username") String username, @Param("pwd") String pwd);


    public User findUserByUsername(@Param("username") String username);

    /**
     * 利用校园号找到对应班级
     * @param username
     * @return
     */
    public UserClass findClassByUsername(String username);

    /**
     * 更新用户基本信息
     */
    public void updateUserInfo(String username, String name,String password);

    public void insertUserBookList(@Param("userId") int userId,@Param("bookId") String bookId);
}
