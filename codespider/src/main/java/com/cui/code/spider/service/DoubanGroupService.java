package com.cui.code.spider.service;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.DoubanGroupDAO;
import com.cui.code.spider.dal.dataobject.DoubanGroupDO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 豆瓣小组业务接口
 *
 * @author CUI
 * @since 2020-01-02
 */
public class DoubanGroupService {

    public List<DoubanGroupDO> pageQueryById(Integer id, int pageSize) {
        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        DoubanGroupDAO doubanGroupDAO = sqlSession.getMapper(DoubanGroupDAO.class);
        return doubanGroupDAO.pageQueryById(id, pageSize);
    }
}
