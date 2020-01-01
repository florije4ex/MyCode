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

    /**
     * update group owner
     *
     * @param doubanGroupDO
     * @return
     */
    int updateOwner(DoubanGroupDO doubanGroupDO);

    /**
     * 根据 id 分页查询小组数据
     *
     * @param id       小组 id
     * @param pageSize
     * @return
     */
    List<DoubanGroupDO> pageQueryById(@Param("id") Integer id, @Param("pageSize") int pageSize);
}
