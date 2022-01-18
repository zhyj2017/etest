package edu.fzu.etest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fzu.etest.bean.*;
import edu.fzu.etest.service.*;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TestController {
    @Autowired
    AnswerService answerService;
    @Autowired
    PaperService paperService;
    @Autowired
    TestService testService;
    @Autowired
    GradeService gradeService;
    @Autowired
    PaperQuestionService paperQuestionService;

    @RequestMapping(value = "/AddQuesToPaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addQuesToPaper(@RequestBody PaperQuestion paperQuestion){
        paperQuestionService.add(paperQuestion);
        Response response = new Response(200,"添加成功",null);
        return response;
    }

    @RequestMapping(value = "/AddPaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addPaper(@RequestBody Paper paper){
        paperService.add(paper);
        Response response = new Response(200,"添加成功",null);
        return response;
    }

    @RequestMapping(value = "/ShowPaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showPaper(@RequestBody Map<String,Object> map){
        long aid = Long.valueOf(map.get("id").toString());
        List<Paper> papers = paperService.list(aid);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("papers",papers);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/AddTest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addTest(@RequestBody Test test){
        long tid = testService.add(test);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("tid",tid);
        Response response = new Response(200,"",map1);
        return response;
    }

//    @RequestMapping(value = "/Mark",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
//    @ResponseBody
//    public Response Mark(@RequestBody Map<String,Object> map){
//        List<Answer> list1 = (List<Answer>)map.get("list");
//        ObjectMapper mapper = new ObjectMapper();
//        List<Answer> list = mapper.convertValue(list1, new TypeReference<List<Answer>>() {});
//        double score = answerService.mark(list);
//        Map<String,Object> map1 = new HashMap<>();
//        map1.put("score",score);
//        Response response = new Response(200,"",map1);
//        return response;
//    }

    @RequestMapping(value = "/AddTest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showScore(@RequestBody Grade grade){
        List<Grade> grades = gradeService.list(grade);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("grades",grades);
        Response response = new Response(200,"",map1);
        return response;
    }
}
