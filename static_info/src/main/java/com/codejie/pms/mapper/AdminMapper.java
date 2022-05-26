package com.codejie.pms.mapper;

import com.codejie.pms.entity.Penetr_check;

import java.util.List;

/**
 * 员工mapper
 */
public interface AdminMapper {
    void add_info(Penetr_check penetr_check);
    Penetr_check select_info(Penetr_check penetr_check);

    void update_info(Penetr_check penetr_check);
}
