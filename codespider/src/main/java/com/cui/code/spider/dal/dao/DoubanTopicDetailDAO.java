package com.cui.code.spider.dal.dao;

import com.cui.code.spider.dal.dataobject.DoubanTopicDetailDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cuishixiang
 * @date 2019-01-23
 */
public interface DoubanTopicDetailDAO {

    /**
     * save topic detail
     *
     * @param doubanTopicDetailDO
     * @return
     */
    int save(DoubanTopicDetailDO doubanTopicDetailDO);

    /**
     * save batch topic detail
     *
     * @param doubanTopicDetailDOList
     * @return
     */
    int saveBatch(@Param("doubanTopicDetailDOList") List<DoubanTopicDetailDO> doubanTopicDetailDOList);
}
