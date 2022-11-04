package com.tbcom.login.controller;


import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.tbcom.jwt.utils.JwtService;
import com.tbcom.login.service.UserService;
import com.tbcom.login.service.impl.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "UserService")
    protected UserService userService;


    @Resource(name = "jacksonJsonView")
    private View jacksonJsonView;

    @RequestMapping(value = "/jwt.do", method = RequestMethod.POST)
    @ResponseBody
    public View createJWT(ModelMap key, @RequestBody UserVO vo, HttpServletResponse response) throws Exception {
        JwtService jwtService = new JwtService();
        if(userService.checkId(vo.getUserId()) == 0){
            key.put("error","사용자가 없습니다.");
            return jacksonJsonView;
        }
        UserVO user = userService.searchUser(vo.getUserId());
        if(!vo.getPassword().equals(user.getPassword())) {
            key.put("error","비밀번호가 틀렸습니다.");
        } else {
                jwtService.createAccessToken(user);
                jwtService.createRefreshToken(user);
                user.setRefreshToken(jwtService.refresh);
                userService.insertToken(user);
            System.out.println(user);
            response.addHeader("access_token",jwtService.access);
            response.addHeader("refresh_token",jwtService.refresh);
        }

        return jacksonJsonView;
    }

}
