package com.codejie.pms;

import com.codejie.pms.controller.AdminController;
import com.codejie.pms.service.AdminService;
import org.apache.catalina.core.ApplicationContext;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@MapperScan("com.codejie.pms.mapper")
@SpringBootApplication
@EnableScheduling
public class PmsApplication {

//    private static ApplicationContext applicationContext;

//    public static void timerRun() {
//        // 一天的毫秒数
//        long daySpan = 24 * 60 * 60 * 1000;
//        // 规定的每天时间15:33:30运行
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 08:57:00");
//        // 首次运行时间
//        try {
//            Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
//            // 如果今天的已经过了 首次运行时间就改为明天
//            if (System.currentTimeMillis() > startTime.getTime()){
//                startTime = new Date(startTime.getTime() + daySpan);
//            }
//            Timer t = new Timer();
//            TimerTask task = new TimerTask() {
//                @Override
//                public void run() {
////                    AdminController.allUser();
////                    AdminController.allUser();
//                }
//            };
//            // 以每24小时执行一次
//            t.schedule(task, startTime, daySpan);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    public static void main(String[] args) {
        SpringApplication.run(PmsApplication.class, args);

//        timerRun();
        System.out.println("项目已启动!");
    }


}
