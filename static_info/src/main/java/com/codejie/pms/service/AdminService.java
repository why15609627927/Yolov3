package com.codejie.pms.service;

import com.codejie.pms.entity.Penetr_check;

import java.util.List;

public interface AdminService {

    void add_info(Penetr_check penetr_check);

    void update_info(Penetr_check penetr_check);

    Penetr_check select_info(Penetr_check penetr_check);

}
