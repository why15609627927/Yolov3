package com.codejie.pms.service.impl;

//import com.codejie.pms.entity.*;
import com.codejie.pms.mapper.EmployeeMapper;
import com.codejie.pms.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;
}
