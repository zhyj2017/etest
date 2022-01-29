package edu.fzu.etest.controller;

import edu.fzu.etest.dto.GradeDTO;
import edu.fzu.etest.service.GradeService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GradeController {
    @Autowired
    GradeService gradeService;

    @RequestMapping(value = "/ShowScore",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showQuestion(@RequestBody Map<String,Object> map){  //管理员查看成绩
        long cid = Long.valueOf(map.get("cid").toString());
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        List<GradeDTO> gradeDTOS = gradeService.listByPage(cid,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("scores",gradeDTOS);
        Response response = new Response(200,"",map1);
        return response;
    }
}
