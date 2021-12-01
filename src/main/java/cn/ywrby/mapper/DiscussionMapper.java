package cn.ywrby.mapper;

import cn.ywrby.domain.Discussion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiscussionMapper {

    public List<Discussion> getDiscussionList();

    public Discussion getDiscussionById(int discussionId);

    int submitDiscussion(Discussion discussion);
}
