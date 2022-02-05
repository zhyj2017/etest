package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Classes;
import edu.fzu.etest.bean.Student;
import edu.fzu.etest.bean.StudentClass;
import edu.fzu.etest.service.QuestionService;
import edu.fzu.etest.service.StudentClassService;
import edu.fzu.etest.service.StudentService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class StudentClassController {
    @Autowired
    StudentClassService studentClassService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/AddStuToClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addStuToClass(@RequestBody List<StudentClass> studentClassList){  //管理员添加学生进入班级
        studentClassService.addStuToClass(studentClassList);
        Response response = new Response();
        response = new Response(response.SUCCESS,"添加成功",null);
        return response;
    }
    @RequestMapping(value = "/DeleteStuFromClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response deleteStuFromClass(@RequestBody Map<String,Object> map){ //管理员将学生移出班级
        long sid=Long.valueOf(map.get("sid").toString());
        long cid=Long.valueOf(map.get("cid").toString());
        studentClassService.deleteStuFromClass(sid,cid);
        Response response = new Response();
        response = new Response(response.SUCCESS,"删除成功",null);
        return response;
    }



}
