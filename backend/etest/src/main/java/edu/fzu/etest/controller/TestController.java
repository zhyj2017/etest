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
    public Response showTests(@RequestBody Map<String,Object> map){  //管理员查看考试
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

    @RequestMapping(value = "/Stu/ShowTest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showTest(@RequestBody Map<String,Object> map){   //学生查看未完成考试
        //获取学生sid
        long sid= Long.valueOf(map.get("sid").toString());
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        //获取已经超时的考试
        List<Test> testList=testService.showOvertime(sid);
        for (int i=0;i<testList.size();i++){
            Grade grade = null;
            grade = gradeService.showGrade(sid,testList.get(i).getId());
            if (grade == null){     //如果学生没有参加该考试，成绩为0
                grade = new Grade();
                grade.setTid(testList.get(i).getId());
                grade.setPid(testList.get(i).getPid());
                grade.setSid(sid);
                grade.setCid(testList.get(i).getCid());
                grade.setScore(0);
                gradeService.add(grade);
            }
        }
        List<Test> tests = testService.showTest(sid,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("tests",tests);
        Response response = new Response(200,"未开始的考试",map1);
        return response;
    }



    @RequestMapping(value = "/Stu/ShowTestFin",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showTestFin(@RequestBody Map<String,Object> map){   //学生查看已经结束的考试
        //获取学生sid
        long sid= Long.valueOf(map.get("sid").toString());
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        //获取已经超时的考试
        List<Test> testList=testService.showOvertime(sid);
        for (int i=0;i<testList.size();i++){
            Grade grade = null;
            grade = gradeService.showGrade(sid,testList.get(i).getId());
            if (grade == null){     //如果学生没有参加该考试，成绩为0
                grade = new Grade();
                grade.setTid(testList.get(i).getId());
                grade.setPid(testList.get(i).getPid());
                grade.setSid(sid);
                grade.setCid(testList.get(i).getCid());
                grade.setScore(0);
                gradeService.add(grade);
            }
        }
        List<Test> tests = testService.showTestFin(sid,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("tests",tests);
        Response response = new Response(200,"已经结束的考试",map1);
        return response;
    }
    @RequestMapping(value = "/Stu/StartExam",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response startExam(@RequestBody Map<String,Object> map){ //学生开始考试
        //获取考试tid
        Long tid= Long.valueOf(map.get("tid").toString());
        Test test = testService.findByTid(tid);
        List<Question> questions = questionService.getQuestionsByPId(test.getPid());
        Map<String,Object> map1 = new HashMap<>();
        map1.put("questions",questions);
        Response response = new Response(200,"",map1);
        return response;
    }
    @RequestMapping(value = "/Stu/SubmitTest",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response submitTest(@RequestBody List<Answer> answerList){   //学生交卷
        double score = answerService.mark(answerList); //批改试卷
        Grade grade = new Grade();
        grade.setTid(answerList.get(0).getTid());
        grade.setSid(answerList.get(0).getSid());
        grade.setPid(answerList.get(0).getPid());
        grade.setCid(studentClassService.showClassId(answerList.get(0).getSid()));
        grade.setScore(score);
        gradeService.add(grade);     //添加成绩
        answerService.saveAnswer(answerList);  //保存答案
        Response response = new Response(200,"提交成功",null);
        return response;
    }


}
