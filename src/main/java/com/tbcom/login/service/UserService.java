package com.tbcom.login.service;

import com.tbcom.login.service.impl.UserVO;

public interface UserService {


    UserVO searchUser(String vo) throws Exception;

    Integer checkId(String id) throws Exception;

    void insertToken(UserVO user) throws Exception;
}
