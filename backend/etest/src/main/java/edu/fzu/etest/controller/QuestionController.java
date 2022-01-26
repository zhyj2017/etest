package edu.fzu.etest.controller;

import edu.fzu.etest.bean.Question;
import edu.fzu.etest.service.QuestionService;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "/AddQuestion",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addQuestion(@RequestBody Question question){   //管理员添加问题
        questionService.add(question);
        Response response = new Response(200,"添加成功",null);
        return response;
    }

    @RequestMapping(value = "/ShowQuestion",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showQuestion(@RequestBody Map<String,Object> map){  //管理员查看问题
        long aid = Long.valueOf(map.get("aid").toString());
        int type = Integer.valueOf("type");
        int pageNum = Integer.valueOf("pageNum");
        int pageSize = Integer.valueOf("pageSize");
        //List<Paper> papers = paperService.list(aid);
        List<Question> questions = questionService.getQuestionsByTypeAndPage(aid,type,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("questions",questions);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/UpdateQuestion",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response updateQuestion(@RequestBody Question question){   //管理员修改问题
        questionService.update(question);
        Response response = new Response(200,"修改成功",null);
        return response;
    }

    @RequestMapping(value = "/DeleteQuestion",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response deleteQuestion(@RequestBody Map<String,Object> map){   //管理员删除问题
        long qid = Long.valueOf(map.get("qid").toString());
        boolean flag = questionService.delete(qid);
        Response response;
        if (flag){
            response = new Response(200,"删除成功",null);
        }else{
            response = new Response(400,"该问题已在试卷中，无法删除",null);
        }
        return response;
    }

}
