package com.cui.code.spider.service;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.DoubanTopicDAO;
import com.cui.code.spider.dal.dataobject.DoubanTopicDO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author cuishixiang
 * @date 2019-01-24
 */
public class DoubanTopicService {

    public List<DoubanTopicDO> pageQuery(String groupCode, Integer id, int pageSize) {
        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        DoubanTopicDAO doubanTopicDAO = sqlSession.getMapper(DoubanTopicDAO.class);
        return doubanTopicDAO.pageByGroupCode(groupCode, id, pageSize);
    }
}
