package cn.ywrby.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ywrby.domain.Classify;
import cn.ywrby.domain.Discussion;
import cn.ywrby.domain.User;
import cn.ywrby.mapper.ClassifyMapper;
import cn.ywrby.mapper.DiscussionMapper;
import cn.ywrby.mapper.UserMapper;
import cn.ywrby.service.DicussionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiscussionServiceImpl implements DicussionService {

    @Autowired
    DiscussionMapper discussionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClassifyMapper classifyMapper;

    @Override
    public List<Discussion> getDiscussionList(Integer page,Integer pageSize) {
        //获取分页插件对象
        PageHelper pageHelper=new PageHelper();
        //开始分页，指定分页参数
        PageMethod.startPage(page,pageSize);
        List<Discussion> discussionList = discussionMapper.getDiscussionList();

        //获取创建人姓名和作业信息
        for (Discussion discussion:discussionList){
            User user=userMapper.findUserById(discussion.getUserId());
            Classify classify=classifyMapper.findClassifyById(discussion.getClassifyId());

            //写入数据
            discussion.setUserName(user.getName());
            discussion.setClassifyName(classify.getName()+"-"+classify.getWork());
        }
        return discussionList;
    }

    @Override
    public Discussion getDiscussionById(int discussionId) {
        Discussion discussion = discussionMapper.getDiscussionById(discussionId);
        //获取创建人姓名和作业信息
        User user=userMapper.findUserById(discussion.getUserId());
        Classify classify=classifyMapper.findClassifyById(discussion.getClassifyId());
        //写入数据
        discussion.setUserName(user.getName());
        discussion.setClassifyName(classify.getName()+"-"+classify.getWork());
        return discussion;
    }

    @Override
    public boolean submitDiscussion(int userId, String discussionStr) {
        //解析JSON字符串，取得用户名与密码信息以及登录token
        JSONObject discussionJson= JSONUtil.parseObj(discussionStr);
        String topic= (String) discussionJson.get("topic");
        String content= (String) discussionJson.get("content");
        int classifyId= (int) discussionJson.get("classifyId");
        Discussion discussion=new Discussion();
        discussion.setContent(content);
        discussion.setTopic(topic);
        discussion.setClassifyId(classifyId);

        discussion.setUserId(userId);
        discussion.setCreateTime(new Date());
        int result=discussionMapper.submitDiscussion(discussion);
        if (result==0){
            return false;
        }else {
            return true;
        }
    }
}
