package com.cui.code.spider.dal.dao.lynk;

import com.cui.code.spider.dal.dataobject.lynk.BookInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预约信息持久化接口
 *
 * @author CUI
 * @date 2019-12-27
 */
public interface BookInfoDAO {

    /**
     * batch save booked info
     */
    int saveBatchBookInfo(@Param("bookInfoDOList") List<BookInfoDO> bookInfoDOList);
}
