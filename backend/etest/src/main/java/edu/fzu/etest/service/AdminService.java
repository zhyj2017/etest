package edu.fzu.etest.service;


import edu.fzu.etest.bean.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    Admin getAdminByAno(String ano);  //根据ano获取一个Admin
    Admin getAdminById(long aid);  //根据aid获取一个Admin
    void update(Admin admin);  //修改Admin
    void add(Admin admin); //添加Admin
}
