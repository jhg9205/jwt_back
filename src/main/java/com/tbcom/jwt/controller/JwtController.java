package com.tbcom.jwt.controller;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.tbcom.login.controller.LoginController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value = "/jwt")
public class JwtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "jacksonJsonView")
    private View jacksonJsonView;

    // 테스트용 호출
    @RequestMapping(value = "/test.do", method = RequestMethod.POST)
    @ResponseBody
    public View test(ModelMap key, @RequestBody Map vo) throws Exception{

        return jacksonJsonView;
    }

}
