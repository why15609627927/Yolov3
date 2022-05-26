package com.codejie.pms.controller;

//import com.codejie.pms.entity.*;
import com.codejie.pms.service.EmployeeService;
import com.codejie.pms.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 员工controller
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @Resource
    private UserService userService;

}
