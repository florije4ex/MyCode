package com.cui.code.spider.dal.dataobject.douban;

import lombok.Data;

import java.util.Date;

/**
 * 话题回复 model
 *
 * @author cuishixiang
 * @since 2020-01-29
 */
@Data
public class DoubanTopicReplyDO {
    private Integer id;
    private String groupCode;
    private Integer topicId;
    private String replyId;
    private String replyName;
    private String replyContent;
    private Date replyTime;
    private Integer likeCount;
    private Integer deleted;
    private Date createTime;
    private Date updateTime;
    private String notes;
}
