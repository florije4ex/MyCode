package com.cui.code.net.model;

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
    明珠山庄温泉浴场("17"),
    世园会预约("20"),
    蓝调温泉("21"),
    太平洋海底世界预约("22");

    private String subscribeId;

    SubscribeIdEnum(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    public String getSubscribeId() {
        return subscribeId;
    }

    /**
     * 通过景区id获取景区枚举对象
     *
     * @param subscribeId 景区id
     * @return 景区对象
     */
    public static SubscribeIdEnum getSubscribeIdEnumById(String subscribeId) {
        for (SubscribeIdEnum subscribeIdEnum : SubscribeIdEnum.values()) {
            if (subscribeIdEnum.getSubscribeId().equals(subscribeId)) {
                return subscribeIdEnum;
            }
        }
        return null;
    }
}
