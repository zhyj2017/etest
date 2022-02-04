package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Classes;
import edu.fzu.etest.service.ClassesService;
import edu.fzu.etest.service.StudentClassService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ClassesController {
    @Autowired
    ClassesService classesService;
    @Autowired
    StudentClassService studentClassService;

    @RequestMapping(value = "/AddClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addClass(@RequestBody Classes classes){
        classesService.addClass(classes);
        Response response = new Response(200,"添加成功",null);
        return response;
    }

    @RequestMapping(value = "/GetClasses",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response getClasses(@RequestBody Map<String,Object> map){  //管理员查看班级(不分页)
        long aid = Long.valueOf(map.get("aid").toString());
        List<Classes> classes = classesService.list(aid);
        Response response = new Response();
        response = new Response(response.SUCCESS,"",classes);
        return response;
    }

    @RequestMapping(value = "/CheckClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response checkClass(@RequestBody Map<String,Object> map){
        long aid = Long.valueOf(map.get("aid").toString());
        long pageNum = Long.valueOf(map.get("pageNum").toString());
        long pageSize=Long.valueOf(map.get("pageSize").toString());
        List<Classes> classes = classesService.checkClass(aid,pageNum,pageSize);
        Response response = new Response();
        response = new Response(response.SUCCESS,"",classes);
        return response;
    }

    @RequestMapping(value = "/UpdateClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response updateClass(@RequestBody Classes classes){
        classesService.updateClass(classes);
        Response response = new Response();
        response = new Response(response.SUCCESS,"修改成功",null);
        return response;
    }

    @RequestMapping(value = "/DeleteClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response deleteClass(@RequestBody Map<String,Object> map){
        long cid = Long.valueOf(map.get("cid").toString());
        //classes表删除
        classesService.deleteClass(cid);
        //student-class表删除
        studentClassService.deleteByCid(cid);
        Response response = new Response();
        response = new Response(response.SUCCESS,"删除成功",null);
        return response;
    }
}
