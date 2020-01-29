package com.cui.code.spider.pipeline.douban;

import com.cui.code.spider.dal.config.DBConfig;
import com.cui.code.spider.dal.dao.douban.DoubanTopicReplyDAO;
import com.cui.code.spider.dal.dataobject.douban.DoubanTopicReplyDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * 处理抓取的结果
 *
 * @author cuishixiang
 * @since 2020-01-29
 */
@Slf4j
public class DoubanTopicReplyPipeline implements Pipeline {

    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        String groupCode = resultItems.get("groupCode");
        Integer topicId = resultItems.get("topicId");
        List<DoubanTopicReplyDO> replyDOList = resultItems.get("doubanTopicReplyDOList");
        if (replyDOList.isEmpty()) {
            log.info("group code:{},topic id:{},没有回复", groupCode, topicId);
            return;
        }

        SqlSession sqlSession = DBConfig.sqlSessionFactory.openSession();
        DoubanTopicReplyDAO replyDAO = sqlSession.getMapper(DoubanTopicReplyDAO.class);
        int rows = replyDAO.saveBatchReply(replyDOList);
        log.info("group code:{},topic id:{},save rows:{}", groupCode, topicId, rows);

        sqlSession.commit();
        sqlSession.close();
    }
}
