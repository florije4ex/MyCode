package com.cui.code.test.common;

/**
 * @author cuishixiang
 * @date 2018-07-30
 */
public enum Province {
    //参考：http://preview.www.mca.gov.cn/article/sj/xzqh/2018/201805/20180506280855.html

    //东北（黑龙江省、吉林省、辽宁省）；
    Heilongjiang("23", "黑龙江省"),
    Jilin("22", "吉林省"),
    Liaoning("21", "辽宁省"),
    //西北（陕西省、甘肃省、青海省、宁夏回族自治区、新疆维吾尔自治区）
    ShanxiLeft("61", "陕西省"),
    Gansu("62", "甘肃省"),
    Qinhai("63", "青海省"),
    Ninxia("64", "宁夏回族自治区"),
    Xinjiang("65", "新疆维吾尔自治区"),
    //西南（四川省、贵州省、云南省、重庆市、西藏自治区）；
    Sichuang("51", "四川省"),
    Guizhou("52", "贵州省"),
    Yunnan("53", "云南省"),
    Chongqing("50", "重庆市"),
    Xizang("54", "西藏自治区"),
    //华南（广东省、广西壮族自治区、海南省、香港特别行政区、澳门特别行政区）；
    Guangdong("44", "广东省"),
    Guangxi("45", "广西壮族自治区"),
    Hainan("46", "海南省"),
    Xianggang("81", "香港特别行政区"),
    Aomen("82", "澳门特别行政区"),
    //华中（河南省、湖北省、湖南省）；
    Henan("41", "河南省"),
    Hubei("42", "湖北省"),
    Hunan("43", "湖南省"),
    //华北（北京市、天津市、河北省、山西省、内蒙古自治区）；
    Beijing("11", "北京市"),
    Tianjin("12", "天津市"),
    Hebei("13", "河北省"),
    ShanxiRight("14", "山西省"),
    Menggu("15", "内蒙古自治区"),
    //华东（上海市、江苏省、浙江省、安徽省、福建省、江西省、山东省、台湾省）；
    Shanghai("31", "上海市"),
    Jiangsu("32", "江苏省"),
    Zhejiang("33", "浙江省"),
    Anhui("34", "安徽省"),
    Fujian("35", "福建省"),
    Jiangxi("36", "江西省"),
    Shangdong("37", "山东省"),
    Taiwan("71", "台湾省");


    //省代码
    private String code;
    //省名称
    private String name;

    private Province(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
