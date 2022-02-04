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


}
