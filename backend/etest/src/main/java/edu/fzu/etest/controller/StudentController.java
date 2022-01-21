package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Student;
import edu.fzu.etest.service.StudentService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/StudentList",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response list(@RequestBody Map<String,Object> map){
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        List<Student> studentList = studentService.listByPage(pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("students",studentList);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/Stu/Manage",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response update(@RequestBody Student student){
        studentService.update(student);
        Response response = new Response(200,"修改成功",null);
        return response;
    }

}
