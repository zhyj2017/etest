package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.fzu.etest.bean.Admin;
import edu.fzu.etest.mapper.AdminMapper;
import edu.fzu.etest.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    public Admin getAdminByAno(String ano){
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("ano", ano));
        return admin;
    }

    public void update(Admin admin){
        adminMapper.updateById(admin);
    }

}
