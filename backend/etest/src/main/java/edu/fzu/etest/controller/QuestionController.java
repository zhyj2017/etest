package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Question;
import edu.fzu.etest.service.QuestionService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "/AddQuestion",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response add(@RequestBody Question question){
        questionService.add(question);
        Response response = new Response(200,"添加成功",null);
        return response;
    }

    @RequestMapping(value = "/ShowQuestion",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showQuestion(@RequestBody Map<String,Object> map){
        int type = Integer.valueOf(map.get("type").toString());
        long aid = Long.valueOf(map.get("aid").toString());
        List<Question> questions = questionService.getQuestionsByType(aid, type);
        Response response = new Response();
        response = new Response(response.SUCCESS,"",questions);
        return response;
    }

}
