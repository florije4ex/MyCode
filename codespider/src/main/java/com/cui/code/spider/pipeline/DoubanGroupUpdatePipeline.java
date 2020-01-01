package com.cui.code.spider.pipeline;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.DoubanGroupDAO;
import com.cui.code.spider.dal.dataobject.DoubanGroupDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author cuishixiang
 * @date 2018-12-26
 */
@Slf4j
public class DoubanGroupUpdatePipeline implements Pipeline {
    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        DoubanGroupDO doubanGroupDO = resultItems.get("doubanGroupDO");
        String code = resultItems.get("code");
        if (doubanGroupDO == null) {
            log.warn("group code:{} 未获取到数据", code);
            return;
        }

        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        DoubanGroupDAO doubanGroupDAO = sqlSession.getMapper(DoubanGroupDAO.class);
        int rows = doubanGroupDAO.updateOwner(doubanGroupDO);
        log.info("group code:{}, save rows:{}", code, rows);
        sqlSession.commit();
        sqlSession.close();
    }
}
