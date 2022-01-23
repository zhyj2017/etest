package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Admin;
import edu.fzu.etest.bean.Grade;
import edu.fzu.etest.bean.Question;
import edu.fzu.etest.service.AdminService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/Manage",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response update(@RequestBody Admin admin){
        adminService.update(admin);
        Response response = new Response(200,"",null);
        return response;
    }

    @RequestMapping(value = "/ShowAnswer",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showAnswer(@RequestBody Map<String,Object> map){
        long cid = Integer.valueOf(map.get("cid").toString());
        long pid = Long.valueOf(map.get("pid").toString());
        List<Grade> gradeList=adminService.showAnswer(cid,pid);
        List<Double>scoreList=null;
        for(int i=0;i<gradeList.size();i++){
            scoreList.add(gradeList.get(i).getScore());
        }
        Response response = new Response();
        response = new Response(response.SUCCESS,"",scoreList);
        return response;
    }
}
