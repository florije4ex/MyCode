package com.code.net.test;

/**
 * 可预订的景区
 *
 * @author cuishixiang
 * @date 2019-01-04
 */
public enum SubscribeIdEnum {

    奥林匹克塔("7"),
    延庆打铁花("8"),
    天津中华曲苑相声会馆("9"),
    蓝调滑雪预约("14"),
    八达岭野生动物园("15"),
    梦幻影院("16"),
    明珠山庄温泉浴场("17");

    private String subscribeId;

    SubscribeIdEnum(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    public String getSubscribeId() {
        return subscribeId;
    }
}
