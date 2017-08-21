package com.cui.code.rpc.service.impl;

import com.cui.code.rpc.service.UserService;

import java.util.UUID;

/**
 * RPC服务接口实现：用户服务
 * Created by cuishixiang on 2017-08-21.
 */
public class UserServiceImpl implements UserService {
    /**
     * 保存用户
     *
     * @param userName 用户名
     * @return 用户名+uuid
     */
    @Override
    public String save(String userName) {
        return userName + UUID.randomUUID().toString().replace("-", "");
    }
}
