package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Admin;
import edu.fzu.etest.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/Manage",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    public void update(@RequestBody Admin admin){
        adminService.update(admin);
    }
}
