package com.codejie.pms.service.impl;

//import com.codejie.pms.entity.*;
import com.github.pagehelper.PageHelper;
import com.codejie.pms.mapper.HrMapper;
import com.codejie.pms.service.HrService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HrServiceImpl implements HrService {

    @Resource
    private HrMapper hrMapper;
}
