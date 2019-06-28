package com.cui.code.spider.dal.dao;

import com.cui.code.spider.dal.dataobject.HospitalDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 114医院数据持久化
 *
 * @author cuiswing
 * @date 2019-06-28
 */
public interface HospitalDAO {

    /**
     * batch save hospital
     *
     * @param hospitalDOList
     * @return
     */
    int saveBatch(@Param("hospitalDOList") List<HospitalDO> hospitalDOList);
}
