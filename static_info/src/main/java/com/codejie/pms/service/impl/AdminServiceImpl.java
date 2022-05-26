package com.codejie.pms.service.impl;

import com.codejie.pms.entity.Penetr_check;
import com.codejie.pms.mapper.AdminMapper;
import com.codejie.pms.service.AdminService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public void add_info(Penetr_check penetr_check) {
        adminMapper.add_info(penetr_check);
    }

    @Override
    public Penetr_check select_info(Penetr_check penetr_check) {
        return adminMapper.select_info(penetr_check);
    }

    @Override
    public void update_info(Penetr_check penetr_check) {
        adminMapper.update_info(penetr_check);
    }

}
