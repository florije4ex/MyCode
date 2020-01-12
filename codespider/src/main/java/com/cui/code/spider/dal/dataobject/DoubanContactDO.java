package com.cui.code.spider.dal.dataobject;

import lombok.Data;

import java.util.Date;

/**
 * 豆瓣联系人
 *
 * @author CUI
 * @date 2020-01-12
 */
@Data
public class DoubanContactDO {
    private Integer id;
    private String fromId;
    private String fromName;
    /**
     * 联系人类型：1-我关注的人，2-关注我的人
     */
    private Integer type;
    private String toId;
    private String toName;
    /**
     * 是否删除：0-默认关注中，1-已不再关注
     */
    private Integer deleted;
    private Date createTime;
    private Date updateTime;
    private String notes;
}
