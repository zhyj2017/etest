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

    public void add(Student student){
        studentMapper.insert(student);
    }

    public void update(Student student){
        //studentMapper.update(null, new UpdateWrapper<Student>().eq("id",student.getId()).set("sphone",student.getSphone()).set("password",student.getPassword()));
        studentMapper.updateById(student);
    }

    public void delete(long sid){
        studentMapper.deleteById(sid);
    }

    public List<Student> listStuInClass(long cid,long pageNum,long pageSize){
        return studentMapper.listStudentInClass(new Page<Student>(pageNum,pageSize),cid).getRecords();
    }

    public List<Student> listStudentNotInClass(long aid, int pageNum, int pageSize){
        return studentMapper.listStudentNotInClass(new Page<Student>(pageNum,pageSize),aid).getRecords();
    }

    public List<Student> getStudentBySnoList(List<Long> sidList){
        //根据学号集合获取学生对象
        List<Student> studentList=studentMapper.selectBatchIds(sidList);
        return studentList;
    }




}
