package com.cui.code.spider.dal.dao.douban;

import com.cui.code.spider.dal.dataobject.douban.DoubanTopicReplyDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cuishixiang
 * @since 2020-01-30
 */
public interface DoubanTopicReplyDAO {

    /**
     * save topic reply
     */
    int saveBatchReply(@Param("doubanTopicReplyDOList") List<DoubanTopicReplyDO> doubanTopicReplyDOList);
}
