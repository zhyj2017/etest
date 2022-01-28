package edu.fzu.etest.controller;

import edu.fzu.etest.bean.*;
import edu.fzu.etest.service.*;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    StudentClassService studentClassService;
    @Autowired
    PaperQuestionService paperQuestionService;
    @Autowired
    QuestionService questionService;
    @Autowired
    GradeService gradeService;
    @Autowired
    AnswerService answerService;

    @RequestMapping(value = "/StudentList",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response list(@RequestBody Map<String,Object> map){
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        List<Student> studentList = studentService.listByPage(pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("students",studentList);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/Stu/ShowDetail",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showDetail(@RequestBody Map<String,Object> map){   //学生查看个人信息
        long sid = Long.valueOf(map.get("sid").toString());
        Student student = studentService.getStudentById(sid);
        Response response = new Response(200,"",student);
        return response;
    }

    @RequestMapping(value = "/Stu/Manage",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response update(@RequestBody Student student){   //学生修改个人信息
        studentService.update(student);
        Response response = new Response(200,"修改成功",null);
        return response;
    }

    @RequestMapping(value = "/Stu/ShowPaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showPaper(@RequestBody Map<String,Object> map){
        //获取学生sid
        Long sid= Long.valueOf(map.get("sid").toString());
        //获取学生班级cid
        Long cid= studentClassService.ShowClassId(sid);
        //获取考试
        List<Test> testList=studentService.showPaper(cid);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("tests",testList);
        Response response = new Response(200,"",testList);
        return response;
    }

    @RequestMapping(value = "/Stu/StartExam",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response startExam(@RequestBody Map<String,Object> map){
        //获取学生sid
        Long sid= Long.valueOf(map.get("id").toString());
        //获取试卷paperId
        Long paperId=Long.valueOf(map.get("paperid").toString());
        List<PaperQuestion> paperQuestionList=paperQuestionService.getQuestionId(paperId);
        List<Long> qidList=null; //考取考题编号
        for(int i=0;i<paperQuestionList.size();i++){
            qidList.add(paperQuestionList.get(i).getQid());
        }
        List<Question> questionList=questionService.getQuestionById(qidList);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("Questions",questionList);
        Response response = new Response(200,"",questionList);
        return response;
    }
    @RequestMapping(value = "/Stu/UpPaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response upPaper(@RequestBody List<Answer> answerList){
        double score = answerService.mark(answerList); //批改试卷
        Grade grade = new Grade();
        grade.setTid(answerList.get(0).getTid());
        grade.setSid(answerList.get(0).getSid());
        grade.setPid(answerList.get(0).getPid());
        grade.setCid(studentClassService.ShowClassId(answerList.get(0).getSid()));
        grade.setScore(score);
        gradeService.add(grade);     //添加成绩
        studentService.UpPaper(answerList);  //保存答案
        Response response = new Response(200,"提交成功",null);
        return response;
    }

    @RequestMapping(value = "/Stu/ShowScore",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showScore(@RequestBody Map<String,Object> map){
        Long sid= Long.valueOf(map.get("id").toString());
        Long pid= Long.valueOf(map.get("paperid").toString());
        Double score=studentService.showScore(sid,pid);
        Response response = new Response(200,"提交成功",score);
        return response;
    }


}
