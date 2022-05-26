package com.codejie.pms.controller;

import com.codejie.pms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    private UserService userService;

    /**
     * Description 跳转登录
     */
    @RequestMapping(value = "")
    public String index() {
        return "login";
    }

}
