package com.cui.code.spider.dal.dao;

import com.cui.code.spider.dal.dataobject.DoubanGroupMemberDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cuishixiang
 * @date 2018-12-28
 */
public interface DoubanGroupMemberDAO {

    /**
     * save group members
     *
     * @param doubanGroupMemberDOList 小组成员
     * @return 保存行数
     */
    int saveBatch(@Param("doubanGroupMemberDOList") List<DoubanGroupMemberDO> doubanGroupMemberDOList);
}
