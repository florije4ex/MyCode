package com.cui.code.spider.pipeline;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.DoubanGroupDAO;
import com.cui.code.spider.dal.dao.DoubanTopicDAO;
import com.cui.code.spider.dal.dataobject.DoubanGroupDO;
import com.cui.code.spider.dal.dataobject.DoubanTopicDO;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * 处理抓取的结果
 *
 * @author cuishixiang
 * @date 2018-12-25
 */
public class DoubanTopicPipeline implements Pipeline {

    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<DoubanTopicDO> doubanTopicDOList = resultItems.get("doubanTopicDOList");

        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        DoubanTopicDAO doubanTopicDAO = sqlSession.getMapper(DoubanTopicDAO.class);
        int rows = doubanTopicDAO.saveBatch(doubanTopicDOList);
        System.out.println(rows);
        sqlSession.commit();
        sqlSession.close();
    }
}
