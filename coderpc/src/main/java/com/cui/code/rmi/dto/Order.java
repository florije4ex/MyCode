package com.cui.code.rmi.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cuishixiang on 2017-11-28.
 */
public class Order implements Serializable {

    private Long id;
    private String orderNo;
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
