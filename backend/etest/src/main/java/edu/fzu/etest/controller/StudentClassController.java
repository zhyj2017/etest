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

    @RequestMapping(value = "/ShowStuInClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showStuInClass(@RequestBody Map<String,Object> map){
        long classid=Long.valueOf(map.get("classid").toString());
        List<StudentClass> studentClasses = studentClassService.ShowStuInClass(classid);
        List<Long> sidlist=null;
        for(int i=0;i<studentClasses.size();i++){
            sidlist.add(studentClasses.get(i).getSid());
        }
        List<Student> studentList=studentService.getStudentBySnoList(sidlist);
        Response response = new Response();
        response = new Response(response.SUCCESS,"",studentList);
        return response;
    }

    @RequestMapping(value = "/AddStuToClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addStuToClass(@RequestBody List<StudentClass> studentClassList){
        studentClassService.AddStuToClass(studentClassList);
        Response response = new Response();
        response = new Response(response.SUCCESS,"添加成功",null);
        return response;
    }



}
