package edu.fzu.etest.controller;

import edu.fzu.etest.dto.AnswerDTO;
import edu.fzu.etest.dto.GradeDTO;
import edu.fzu.etest.mapper.AnswerMapper;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AnswerController {
    @Autowired
    AnswerMapper answerMapper;

    @RequestMapping(value = "/Stu/ShowAnswer",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showQuestion(@RequestBody Map<String,Object> map){  //管理员查看成绩
        long sid = Long.valueOf(map.get("sid").toString());
        int tid = Integer.valueOf(map.get("tid").toString());
        List<AnswerDTO> answerDTOList=answerMapper.listAnswer(sid,tid);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("answers",answerDTOList);
        Response response = new Response(200,"",map1);
        return response;
    }

}
