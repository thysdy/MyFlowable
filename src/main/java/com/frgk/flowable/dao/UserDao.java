package com.frgk.flowable.dao;

import com.frgk.flowable.entity.User;

import java.util.List;

public interface UserDao {
    /**
     * 通过用户Id获取用户信息
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * 获取所有用户信息
     * @return
     */
    List<User> getAllUsers();
}
