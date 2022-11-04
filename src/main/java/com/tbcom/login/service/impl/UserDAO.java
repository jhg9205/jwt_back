package com.tbcom.login.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("userDao")
public class UserDAO implements UserMapper{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO selectUserById(String id) {return userMapper.selectUserById(id);}

    @Override
    public Integer checkId(String id){
        return userMapper.checkId(id);
    }

    @Override
    public void insertToken(UserVO user){ userMapper.insertToken(user);}
}
