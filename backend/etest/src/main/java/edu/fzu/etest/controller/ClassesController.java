package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Classes;
import edu.fzu.etest.service.ClassesService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ClassesController {
    @Autowired
    ClassesService classesService;

    @RequestMapping(value = "/AddClass",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addClass(@RequestBody Classes classes){
        classesService.AddClass(classes);
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
        long id = Long.valueOf(map.get("id").toString());
        List<Classes> classes = classesService.CheckClass(id);
        Response response = new Response();
        response = new Response(response.SUCCESS,"",classes);
        return response;
    }
}
