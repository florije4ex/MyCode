package com.cui.code.spider.dal.dao;

import com.cui.code.spider.dal.dataobject.DoubanTopicDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cuishixiang
 * @date 2018-12-25
 */
public interface DoubanTopicDAO {

    /**
     * save topic
     *
     * @param doubanTopicDOList
     * @return
     */
    int saveBatch(@Param("doubanTopicDOList") List<DoubanTopicDO> doubanTopicDOList);
}
