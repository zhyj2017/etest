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
    @Autowired
    StudentClassService studentClassService;


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
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
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

    @RequestMapping(value = "/Stu/ShowPaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showPaper(@RequestBody Map<String,Object> map){
        //获取学生sid
        long sid= Long.valueOf(map.get("sid").toString());
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        //获取学生班级cid
        Long cid= studentClassService.showClassId(sid);
        //获取考试
        List<Test> testList=testService.showPaper(cid,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("tests",testList);
        Response response = new Response(200,"未开始的考试",testList);
        return response;
    }

    @RequestMapping(value = "/Stu/ShowTestFin",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showTestFin(@RequestBody Map<String,Object> map){
        //获取学生sid
        long sid= Long.valueOf(map.get("sid").toString());
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        //获取学生班级cid
        long cid= studentClassService.showClassId(sid);
        //获取考试
        List<Test> testList=testService.showPaper(cid,pageNum,pageSize);
        for(int i=0;i<testList.size();i++){
            //先根据tid和sid查找grade表判断是否为空,查找为空插入成绩为0的数据
            if(gradeService.showGrade(sid,testList.get(i).getId())==null){
                Grade grade=new Grade();
                grade.setCid(cid);
                grade.setSid(sid);
                grade.setScore(0);
                grade.setPid(testList.get(i).getPid());
                grade.setTid(testList.get(i).getId());
                gradeService.add(grade);
            }
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("tests",testList);
        Response response = new Response(200,"已经结束的考试",testList);
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
