package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Admin;
import edu.fzu.etest.bean.Grade;
import edu.fzu.etest.bean.Question;
import edu.fzu.etest.service.AdminService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/Manage",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response update(@RequestBody Admin admin){ //管理员修改个人信息
        adminService.update(admin);
        Response response = new Response(200,"",null);
        return response;
    }

    @RequestMapping(value = "/Regist",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response add(@RequestBody Admin admin){  //注册管理员
        adminService.add(admin);
        Response response = new Response(200,"注册成功",null);
        return response;
    }

    @RequestMapping(value = "/ShowDetail",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showDetail(@RequestBody Map<String,Object> map){  //管理员查看个人信息
        long aid = Long.valueOf(map.get("aid").toString());
        Admin admin = adminService.getAdminById(aid);
        Response response = new Response(200,"",admin);
        return response;
    }

}
