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

    /**
     * 根据小组code分页查询topic
     *
     * @param groupCode 小组code
     * @param id        topic AUTO_INCREMENT id ，PRIMARY KEY
     * @param pageSize  分页大小
     * @return query result
     */
    List<DoubanTopicDO> pageByGroupCode(@Param("groupCode") String groupCode, @Param("id") Integer id, @Param("pageSize") int pageSize);
}
