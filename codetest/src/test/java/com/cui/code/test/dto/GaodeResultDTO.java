package com.cui.code.test.dto;

/**
 * @author cuishixiang
 * @date 2018-06-14
 */
public class GaodeResultDTO {
    /**
     * 返回状态  1：成功；0：失败
     */
    private Integer status;
    /**
     * 返回的状态信息  status为0时，info返回错误原；否则返回“OK”。详情参阅info状态表
     */
    private String info;
    private String infocode;
    /**
     * 转换之后的坐标。若有多个坐标，则用 “;”进行区分和间隔
     */
    private String locations;

    public Integer getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public String getInfocode() {
        return infocode;
    }

    public String getLocations() {
        return locations;
    }
}
