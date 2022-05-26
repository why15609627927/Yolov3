package com.codejie.pms.controller;

import com.codejie.pms.service.UserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description user
 */
//@RestController
@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

//    /**
//     * Description 登录，可以加入 @CrossOrigin 支持跨域。
//     * @param user 用户输入的账号和密码
//     * @return String
//     */
//    @RequestMapping(value = "/login")
//    public ModelAndView login(User user, HttpServletRequest request) {
//        User user1 = userService.userLogin(user);
//        ModelAndView mv = new ModelAndView("login");
//        if (user1 == null) {
//            return mv;
//        } else{
//            request.getSession().setAttribute("user",user1);
//            if(user.getUserId().equals("gyqh")){
//                mv.setViewName("index_gyqh");
//                mv.addObject("user",user1);
//            }else if(user.getUserId().equals("gytz1") || user.getUserId().equals("gytz2")){
//                mv.setViewName("index_gytz");
//                mv.addObject("user",user1);
//            }
//
//
//            return mv;
//        }
//    }
}
