package cn.ywrby.service;

import cn.ywrby.domain.Discussion;

import java.util.List;

public interface DicussionService {

    public List<Discussion> getDiscussionList(Integer page,Integer pageSize);

    public Discussion getDiscussionById(int discussionId);

    boolean submitDiscussion(int userId, String discussionStr);
}
