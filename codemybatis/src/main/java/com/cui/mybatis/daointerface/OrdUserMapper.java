package com.cui.mybatis.daointerface;

import com.cui.mybatis.dataobject.OrdUser;

import java.util.List;

/**
 * Mapper接口（相当于Dao接口），由Mybatis框架根据接口定义创建接口的动态代理对象
 * Created by cuishixiang on 2017-09-06.
 */
public interface OrdUserMapper {

    /**
     * @param id
     * @return
     */
    OrdUser queryUserById(Integer id);

    /**
     * @param username
     * @return
     */
    List<OrdUser> queryUserByUsername(String username);


    int saveUser(OrdUser ordUser);

    int saveUserReturnKey(OrdUser ordUser);

    int updateUser(OrdUser ordUser);

    int deleteUserById(Integer id);
}
