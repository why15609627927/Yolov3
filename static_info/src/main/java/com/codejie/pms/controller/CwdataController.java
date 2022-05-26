package com.codejie.pms.controller;

//import com.codejie.pms.entity.*;
import com.codejie.pms.service.AdminService;
import com.codejie.pms.service.EmployeeService;
import com.codejie.pms.service.UserService;
import com.codejie.pms.util.DateUtil;
import com.codejie.pms.util.ImportExcelUtil;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/cwdata")
public class CwdataController {

    @Resource
    private AdminService adminService;
}
