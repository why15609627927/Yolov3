package com.codejie.pms.controller;

//import com.codejie.pms.entity.*;
import com.codejie.pms.service.HrService;
import com.codejie.pms.service.UserService;
import com.codejie.pms.util.DateUtil;
import com.codejie.pms.util.ImportExcelUtil;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description user
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/hr")
public class HrController {

    @Resource
    private HrService hrService;

    @Resource
    private UserService userService;


}