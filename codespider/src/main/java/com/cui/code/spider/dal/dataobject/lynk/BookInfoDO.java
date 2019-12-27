package com.cui.code.spider.dal.dataobject.lynk;

import lombok.Data;

import java.util.Date;

/**
 * 预约信息
 *
 * @author CUI
 * @date 2019-12-27
 */
@Data
public class BookInfoDO {
    private Integer id;
    private Integer bookId;
    private String bookName;
    private Date bookDate;
    private String bookStatus;
    private String name;
    private String cardNo;
    private String cardType;
    private Date createTime;
    private Date updateTime;
}
