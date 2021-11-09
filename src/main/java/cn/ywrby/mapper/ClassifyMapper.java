package cn.ywrby.mapper;

import cn.ywrby.domain.Classify;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassifyMapper {
    public List<Classify> getClassifyList();
}
