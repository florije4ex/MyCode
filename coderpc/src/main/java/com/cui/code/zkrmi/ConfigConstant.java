package com.cui.code.zkrmi;

/**
 * 配置常量
 * Created by cuishixiang on 2017-11-28.
 */
public final class ConfigConstant {
    public static final String ZK_CONNECTION_STRING = "localhost:2181";
    public static final int ZK_SESSION_TIMEOUT = 5000;
    public static final String ZK_REGISTRY_PATH = "/registry";
    public static final String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";
}
