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


    @RequestMapping(value = "/AddTest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addTest(@RequestBody Map<String,Object> map){  //管理员添加考试
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
        testService.add(test);
        Response response = new Response(200,"添加成功",null);
        return response;
    }

    @RequestMapping(value = "/ShowTest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showTest(@RequestBody Map<String,Object> map){  //管理员查看考试
        long aid = Long.valueOf(map.get("aid").toString());
        int pageNum = Integer.valueOf("pageNum");
        int pageSize = Integer.valueOf("pageSize");
        //List<Paper> papers = paperService.list(aid);
        List<Test> tests = testService.list(aid,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("tests",tests);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/UpdateTest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response updateTest(@RequestBody Map<String,Object> map){  //管理员修改考试
        Test test = new Test();
        long id = Long.valueOf(map.get("id").toString());
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
        test.setId(id);
        test.setTname(tname);
        test.setStarttime(sdate);
        test.setEndtime(edate);
        test.setDescription(description);
        test.setCid(cid);
        test.setPid(pid);
        test.setAid(aid);
        testService.update(test);
        Response response = new Response(200,"修改成功",null);
        return response;
    }

    @RequestMapping(value = "/DeleteTest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response deleteTest(@RequestBody Map<String,Object> map){   //管理员删除考试
        long tid = Long.valueOf(map.get("tid").toString());
        testService.delete(tid);
        Response response = new Response(200,"删除成功",null);
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


}
