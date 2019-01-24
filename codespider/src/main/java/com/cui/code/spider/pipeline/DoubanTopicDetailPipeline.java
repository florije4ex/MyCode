package com.cui.code.spider.pipeline;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.DoubanTopicDetailDAO;
import com.cui.code.spider.dal.dataobject.DoubanTopicDetailDO;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 处理抓取的结果
 *
 * @author cuishixiang
 * @date 2019-01-23
 */
public class DoubanTopicDetailPipeline implements Pipeline {

    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        DoubanTopicDetailDO doubanTopicDetailDO = resultItems.get("doubanTopicDetailDO");

        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        DoubanTopicDetailDAO doubanTopicDetailDAO = sqlSession.getMapper(DoubanTopicDetailDAO.class);
        int rows = doubanTopicDetailDAO.save(doubanTopicDetailDO);
        System.out.println(doubanTopicDetailDO.getTopicId());
        sqlSession.commit();
        sqlSession.close();
    }
}
