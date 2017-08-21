package com.cui.code.rpc.service;

/**
 * RPC服务接口：用户服务
 * Created by cuishixiang on 2017-08-21.
 */
public interface UserService {

    /**
     * 保存用户
     *
     * @param userName 用户名
     * @return 用户名+uuid
     */
    String save(String userName);
}
