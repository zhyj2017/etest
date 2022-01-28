package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Admin;
import edu.fzu.etest.bean.Student;
import edu.fzu.etest.service.AdminService;
import edu.fzu.etest.service.StudentService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    StudentService studentService;

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/LoginQuest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestBody Map<String,Object> map1){  //登录
        String no = map1.get("no").toString();
        String password = map1.get("password").toString();
        Response response = new Response();
        Student student = studentService.getStudentBySno(no);
        Admin admin = adminService.getAdminByAno(no);
        if (admin != null){
            if (!admin.getPassword().equals(password)){
                response = new Response(response.FAILURE,"密码错误",null);
            }else{
                Map<String,Object> map = new HashMap<>();
                map.put("type",1);
                map.put("id",admin.getId());
                map.put("name",admin.getAname());
                response = new Response(response.SUCCESS,"登录成功",map);
            }
        }else if (student != null){
            if (!student.getPassword().equals(password)){
                response = new Response(response.FAILURE,"密码错误",null);
            }else{
                Map<String,Object> map = new HashMap<>();
                map.put("type",2);
                map.put("id",student.getId());
                map.put("name",student.getSname());
                response = new Response(response.SUCCESS,"登录成功",map);
            }
        }else {
            response = new Response(response.FAILURE,"账号不存在",null);
        }
        return response;
    }
}
