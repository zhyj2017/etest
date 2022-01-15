package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Admin;
import edu.fzu.etest.service.AdminService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
