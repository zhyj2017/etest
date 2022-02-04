package edu.fzu.etest.controller;

import edu.fzu.etest.bean.*;
import edu.fzu.etest.service.*;
import edu.fzu.etest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaperController {
    @Autowired
    PaperService paperService;
    @Autowired
    QuestionService questionService;
    @Autowired
    PaperQuestionService paperQuestionService;
    @Autowired
    GradeService gradeService;
    @Autowired
    AnswerService answerService;
    @Autowired
    StudentClassService studentClassService;
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/AddQuesToPaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addQuesToPaper(@RequestBody List<PaperQuestion> paperQuestions){   //添加问题到考试里
        for (int i=0;i<paperQuestions.size();i++){
            paperQuestionService.add(paperQuestions.get(i));
        }
        Response response = new Response(200,"添加成功",null);
        return response;
    }

    @RequestMapping(value = "/AddPaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response addPaper(@RequestBody Paper paper){  //管理员添加考试
        paperService.add(paper);
        Response response = new Response(200,"添加成功",null);
        return response;
    }

    @RequestMapping(value = "/GetPapers",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response getPaper(@RequestBody Map<String,Object> map){  //管理员查看试卷(不分页)
        long aid = Long.valueOf(map.get("aid").toString());
        List<Paper> papers = paperService.list(aid);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("papers",papers);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/ShowPaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response showPaper(@RequestBody Map<String,Object> map){  //管理员查看试卷
        long aid = Long.valueOf(map.get("aid").toString());
        int pageNum = Integer.valueOf(map.get("pageNum").toString());
        int pageSize = Integer.valueOf(map.get("pageSize").toString());
        List<Paper> papers = paperService.listByPage(aid,pageNum,pageSize);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("papers",papers);
        Response response = new Response(200,"",map1);
        return response;
    }

    @RequestMapping(value = "/UpdatePaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response updatePaper(@RequestBody Paper paper){   //管理员修改试卷
        paperService.update(paper);
        Response response = new Response(200,"修改成功",null);
        return response;
    }

    @RequestMapping(value = "/DeletePaper",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response deletePaper(@RequestBody Map<String,Object> map){   //管理员删除试卷
        long pid = Long.valueOf(map.get("pid").toString());
        boolean flag = paperService.delete(pid);
        Response response;
        if (flag){
            response = new Response(200,"删除成功",null);
        }else{
            response = new Response(400,"该试卷已被作为考试使用中，无法删除",null);
        }
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
        grade.setCid(studentClassService.showClassId(answerList.get(0).getSid()));
        grade.setScore(score);
        gradeService.add(grade);     //添加成绩
        studentService.UpPaper(answerList);  //保存答案
        Response response = new Response(200,"提交成功",null);
        return response;
    }
}
