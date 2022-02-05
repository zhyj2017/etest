package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.fzu.etest.bean.Admin;
import edu.fzu.etest.bean.Grade;
import edu.fzu.etest.mapper.AdminMapper;
import edu.fzu.etest.mapper.GradeMapper;
import edu.fzu.etest.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    GradeMapper gradeMapper;

    public Admin getAdminByAno(String ano){
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("ano", ano));
        return admin;
    }

    public Admin getAdminById(long aid){
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("id", aid));
        return admin;
    }

    public void update(Admin admin){
        adminMapper.updateById(admin);
    }

    public void add(Admin admin){
        adminMapper.insert(admin);
    }

}
