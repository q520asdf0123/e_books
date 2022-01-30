package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.SearchHistory;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface SearchHistoryMapper {

    int insert(SearchHistory history);
}
