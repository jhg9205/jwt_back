package com.tbcom.login.service.impl;

import org.springframework.security.core.userdetails.User;

public interface UserMapper {
    UserVO selectUserById(String id);

    Integer checkId(String id);

    void insertToken(UserVO user);
}
