package com.codejie.pms.service.impl;

//import com.codejie.pms.entity.*;
import com.codejie.pms.mapper.UserMapper;
import com.codejie.pms.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
}
