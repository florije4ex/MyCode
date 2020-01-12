package com.cui.code.spider.service;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.DoubanContactDAO;
import com.cui.code.spider.dal.dataobject.DoubanContactDO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * contact service
 *
 * @author CUI
 * @date 2020-01-12
 */
public class DoubanContactService {

    public List<DoubanContactDO> listContactByType(String fromId, int type) {
        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        DoubanContactDAO doubanContactDAO = sqlSession.getMapper(DoubanContactDAO.class);
        return doubanContactDAO.listContactByType(fromId, type);
    }
}
