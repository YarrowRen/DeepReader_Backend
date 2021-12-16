package cn.ywrby.service.impl;

import cn.ywrby.domain.*;
import cn.ywrby.mapper.BookMapper;
import cn.ywrby.mapper.UserMapper;
import cn.ywrby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;


    @Override
    public User verify(User user) {
        String username=user.getUsername();
        String pwd= user.getPassword();
        user = userMapper.findUserByUserAndPwd(username, pwd);
        if(user!=null){
            return user;
        }else {
            return null;
        }
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userMapper.findUserByUsername(username);
        List<Integer> permissionList=userMapper.getUserPermission(user.getId());
        List<String> roles=new ArrayList<>();
        for(int i:permissionList){
            if (i==0){
                roles.add("editor");
            }else if (i==1){
                roles.add("admin");
            }
        }
        //List<String> roles= Arrays.asList("admin","editor");
        user.setRoles(roles);
        return user;
    }

    @Override
    public UserClass findClassByUsername(String username) {
        UserClass userClass=userMapper.findClassByUsername(username);
        return userClass;
    }

    @Override
    public boolean updateUserInfo(String username, String name, String oldPassword, String newPassword) {
        //System.out.println(username+" : "+oldPassword);
        User user = userMapper.findUserByUserAndPwd(username, oldPassword);
        if(user!=null){
            //用户对象不为空，表示旧密码输入正确，允许用户更新密码
            userMapper.updateUserInfo(username,name,newPassword);
            return true;
        }else {
            //对象为空，密码输入错误
            return false;
        }
    }

    @Override
    public boolean insertKWLForm(KWL kwl) {
        int kwlId=userMapper.insertKWLForm(kwl);
        System.out.println(kwlId);
        if(kwlId!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void insertUserRead(int userId, int bookId) {
        userMapper.insertUserRead(userId,bookId);
    }

    @Override
    public boolean insertQuestionForm(QuestionForm question) {
        int questionId= userMapper.insertQuestionForm(question);
        System.out.println(questionId);
        if(questionId!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void insertUserAnswer(int userId, int bookId) {
        userMapper.insertUserAnswer(userId,bookId);
    }

    @Override
    public List<Heat> getUserHeat(int userId) {
        List<Heat> userHeat = userMapper.getUserHeat(userId);
        return userHeat;
    }


}
