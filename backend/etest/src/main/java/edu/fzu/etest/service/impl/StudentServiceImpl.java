package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.*;
import edu.fzu.etest.mapper.*;
import edu.fzu.etest.service.PaperQuestionService;
import edu.fzu.etest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TestMapper testMapper;
    @Autowired
    PaperQuestionMapper paperQuestionMapper;
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    GradeMapper gradeMapper;

    public Student getStudentBySno(String sno){
        Student student = studentMapper.selectOne(new QueryWrapper<Student>().eq("sno", sno));
        return student;
    }

    public Student getStudentById(long sid){
        Student student = studentMapper.selectOne(new QueryWrapper<Student>().eq("id", sid));
        return student;
    }

    public List<Student> listByPage(int pageNum, int pageSize){
        List<Student> students = new ArrayList<>();
        students = studentMapper.selectPage(new Page<Student>(pageNum,pageSize),null).getRecords();
        return students;
    }

    public void update(Student student){
        studentMapper.update(null, new UpdateWrapper<Student>().eq("id",student.getId()).set("sphone",student.getSphone()).set("password",student.getPassword()));
    }

    public List<Student> getStudentBySnoList(List<Long> sidList){
        //根据学号集合获取学生对象
        List<Student> studentList=studentMapper.selectBatchIds(sidList);
        return studentList;
    }

    public List<Test> showPaper(long id){
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //根据班级编号cid和还没到考试开始时间来查询未完成考试
        List<Test> testList=testMapper.selectList(new QueryWrapper<Test>().eq("cid",id).ge("starttime",formatter.format(date)));
        return testList;
    }

    public List<PaperQuestion> startExam(long sid, long pid)
    {
        //先根据试卷pid获取qid
        List<PaperQuestion>paperQuestionList=paperQuestionMapper.selectList(new QueryWrapper<PaperQuestion>().eq("pid",pid));
        return paperQuestionList;
    }

    public void UpPaper(List<Answer> answerList){
        for(int i=0;i<answerList.size();i++){
            answerMapper.insert(answerList.get(i));
        }
    }

    public Double showScore(Long sid,Long pid){
        Grade grade=gradeMapper.selectOne(new QueryWrapper<Grade>().eq("sid",sid).eq("pid",pid));
        return grade.getScore();
    }
}
