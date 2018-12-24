package com.cui.code.spider.pipeline;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.DoubanGroupDAO;
import com.cui.code.spider.dal.dataobject.DoubanGroupDO;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * 处理抓取的结果
 *
 * @author cuishixiang
 * @date 2018-12-24
 */
public class DoubanGroupPipeline implements Pipeline {

    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<DoubanGroupDO> doubanGroupDOList = resultItems.get("doubanGroupDOList");

        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        // 从sqlSession中获取Mapper接口的代理对象
        DoubanGroupDAO doubanGroupDAO = sqlSession.getMapper(DoubanGroupDAO.class);
        int rows = doubanGroupDAO.saveBatch(doubanGroupDOList);
        System.out.println(rows);
        sqlSession.commit();
        sqlSession.close();
    }
}
