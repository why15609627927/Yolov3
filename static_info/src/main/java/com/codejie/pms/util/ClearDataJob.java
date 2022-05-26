package com.codejie.pms.util;

import com.codejie.pms.service.AdminService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ClearDataJob {

    //http://cron.qqe2.com 快捷生成cron表达式
    //注入service对象 方便调用
    @Resource
    private AdminService adminService;

//    */5 * * * * ?
//    "0 0 1 * * ?"
    @Scheduled(cron = "0 0 1 * * ?")
    public void clearDataJob() {
        System.out.println("---------定时任务开始执行---------" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        clearData(adminService);
        System.out.println("---------定时任务执行成功---------" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    /**
     * 清理数据
     *
     * @param adminService
     */
    private static void clearData(AdminService adminService) {
    }
}
