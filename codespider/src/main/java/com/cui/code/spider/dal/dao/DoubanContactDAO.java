package com.cui.code.spider.dal.dao;

import com.cui.code.spider.dal.dataobject.DoubanContactDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 豆瓣联系人 Mapper
 *
 * @author CUI
 * @date 2020-01-12
 */
public interface DoubanContactDAO {

    /**
     * save contact batch
     */
    int saveContactBatch(@Param("fromId") String fromId, @Param("fromName") String fromName, @Param("type") Integer type,
                         @Param("doubanContactDOList") List<DoubanContactDO> doubanContactDOList);

    DoubanContactDO getFirstContact(@Param("fromId") String fromId, @Param("type") Integer type);

    List<DoubanContactDO> listContactByType(@Param("fromId") String fromId, @Param("type") Integer type);

    int updateByToIdList(@Param("fromId") String fromId, @Param("fromName") String fromName,
                         @Param("removeToIdList") List<String> removeToIdList,
                         @Param("newStatus") Integer newStatus, @Param("notes") String notes);
}
