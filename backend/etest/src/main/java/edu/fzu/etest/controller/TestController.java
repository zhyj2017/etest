package edu.fzu.etest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fzu.etest.bean.*;
import edu.fzu.etest.service.*;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
        long aid = Long.valueOf(map.get("id").toString());
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
        int pageNum = Integer.valueOf("pageNum");
        int pageSize = Integer.valueOf("pageSize");
        //List<Paper> papers = paperService.list(aid);
        List<Paper> papers = paperService.listByPage(aid,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("papers",papers);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/AddTest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addTest(@RequestBody Map<String,Object> map){
        Test test = new Test();
        String tname = map.get("tname").toString();
        String starttime = map.get("starttime").toString();
        String endtime = map.get("endtime").toString();
        String description = map.get("description").toString();
        long pid = Long.valueOf(map.get("pid").toString());
        long cid = Long.valueOf(map.get("cid").toString());
        long aid = Long.valueOf(map.get("aid").toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sdate = null;
        Date edate = null;
        try{
            sdate = sdf.parse(starttime);
            edate = sdf.parse(endtime);
        } catch (Exception e){
            e.printStackTrace();
        }
        test.setTname(tname);
        test.setStarttime(sdate);
        test.setEndtime(edate);
        test.setDescription(description);
        test.setCid(cid);
        test.setPid(pid);
        test.setAid(aid);
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

    @RequestMapping(value = "/ShowScore",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showScore(@RequestBody Map<String,Object> map){
        //List<Grade> grades = gradeService.list(grade);
        long cid = Long.valueOf(map.get("cid").toString());
        long tid = Long.valueOf(map.get("tid").toString());
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        List<Grade> grades = gradeService.listByPage(cid,tid,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("grades",grades);
        Response response = new Response(200,"",map1);
        return response;
    }
}
