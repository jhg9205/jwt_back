package com.tbcom.login.service.impl;

import com.tbcom.login.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userDao")
    private UserDAO userDAO;

    @Override
    public UserVO searchUser(String vo) {
        return userDAO.selectUserById(vo);
    }

    @Override
    public Integer checkId(String id) {
        return userDAO.checkId(id);
    }

    @Override
    public void insertToken(UserVO user){userDAO.insertToken(user);}
}
