package com.codejie.pms.controller;

import com.codejie.pms.entity.Penetr_check;
import com.codejie.pms.service.AdminService;
import com.codejie.pms.service.HrService;
import com.codejie.pms.service.UserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/admin")
public class AdminController {

    @Resource
    private HrService hrService;
    @Resource
    private AdminService adminService;
    @Resource
    private UserService userService;


    /**
     * Description 验证穿透式验证信息
     */
    @RequestMapping("/select_info")
    @ResponseBody
    public boolean select_info(String userName, String phone) {
        String curr_date= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Penetr_check penetr_check = new Penetr_check();
        penetr_check.setUserName(userName);
        penetr_check.setPhone(phone);
        penetr_check.setCurrdate(curr_date);
        Penetr_check penetr_check_info = adminService.select_info(penetr_check);
        if(penetr_check_info == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Description 提交穿透式验证信息
     */
    @RequestMapping("/add_info")
    @ResponseBody
    public boolean add_info(String userName, String phone, String terminalName, String terminalType, String APPID, String counter) {
        String curr_date= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Penetr_check penetr_check = new Penetr_check();
        penetr_check.setUserName(userName);
        penetr_check.setPhone(phone);
        penetr_check.setTerminalName(terminalName);
        penetr_check.setTerminalType(terminalType);
        penetr_check.setAPPID(APPID);
        penetr_check.setCounter(counter);
        penetr_check.setCurrdate(curr_date);
        adminService.add_info(penetr_check);
        return true;
    }

    /**
     * Description 验证穿透式验证信息
     */
    @RequestMapping("/update_info")
    @ResponseBody
    public boolean update_info(String userName, String phone, String terminalName, String terminalType, String APPID, String counter) {
        String curr_date= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Penetr_check penetr_check = new Penetr_check();
        penetr_check.setUserName(userName);
        penetr_check.setPhone(phone);
        penetr_check.setTerminalName(terminalName);
        penetr_check.setTerminalType(terminalType);
        penetr_check.setAPPID(APPID);
        penetr_check.setCounter(counter);
        penetr_check.setCurrdate(curr_date);
        adminService.update_info(penetr_check);
        return true;
    }


}
