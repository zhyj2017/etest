package edu.fzu.etest.controller;

import edu.fzu.etest.bean.*;
import edu.fzu.etest.service.*;
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
    @Autowired
    StudentClassService studentClassService;


    @RequestMapping(value = "/ShowStudent",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response list(@RequestBody Map<String,Object> map){   //管理员查看学生信息
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        List<Student> studentList = studentService.listByPage(pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("students",studentList);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/AddStudent",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addStudent(@RequestBody Student student){   //管理员修改学生信息
        studentService.add(student);
        Response response = new Response(200,"添加成功",null);
        return response;
    }

    @RequestMapping(value = "/UpdateStudent",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response updateStudent(@RequestBody Student student){   //管理员修改学生信息
        studentService.update(student);
        Response response = new Response(200,"修改成功",null);
        return response;
    }

    @RequestMapping(value = "/DeleteStudent",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response deleteStudent(@RequestBody Map<String,Object> map){   //管理员修改学生信息
        long sid = Long.valueOf(map.get("sid").toString());
        studentService.delete(sid);
        studentClassService.deleteBySid(sid);
        Response response = new Response(200,"删除成功",null);
        return response;
    }

    @RequestMapping(value = "/ShowStuInClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showStuInClass(@RequestBody Map<String,Object> map){  //管理员查看班级内的学生
        long aid=Long.valueOf(map.get("aid").toString());
        long cid=Long.valueOf(map.get("cid").toString());
        long pageNum=Long.valueOf(map.get("pageNum").toString());
        long pageSize=Long.valueOf(map.get("pageSize").toString());
        List<Student> stuList = studentService.listStuInClass(cid,pageNum,pageSize);
        Response response = new Response();
        response = new Response(response.SUCCESS,"",stuList);
        return response;
    }

    @RequestMapping(value = "/CheckStudent",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response checkStudent(@RequestBody Map<String,Object> map){   //管理员查看未加入班级可以分班的学生
        long aid = Long.valueOf(map.get("aid").toString());
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        List<Student> studentList = studentService.listStudentNotInClass(aid,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("students",studentList);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/Stu/ShowDetail",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showDetail(@RequestBody Map<String,Object> map){   //学生查看个人信息
        long sid = Long.valueOf(map.get("sid").toString());
        Student student = studentService.getStudentById(sid);
        Response response = new Response(200,"",student);
        return response;
    }

    @RequestMapping(value = "/Stu/Manage",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response update(@RequestBody Student student){   //学生修改个人信息
        studentService.update(student);
        Response response = new Response(200,"修改成功",null);
        return response;
    }






}
