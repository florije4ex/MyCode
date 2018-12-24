package com.cui.code.spider.dal.dao;

import com.cui.code.spider.dal.dataobject.DoubanGroupDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cuishixiang
 * @date 2018-12-24
 */
public interface DoubanGroupDAO {

    /**
     * save group
     *
     * @param doubanGroupDOList
     * @return
     */
    int saveBatch(@Param("doubanGroupDOList") List<DoubanGroupDO> doubanGroupDOList);
}
