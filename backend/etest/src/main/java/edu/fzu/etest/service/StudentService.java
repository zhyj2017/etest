package edu.fzu.etest.service;

import edu.fzu.etest.bean.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student getStudentBySno(String sno);  //根据sno获得学生
    Student getStudentById(long sid);  //根据sid获得学生
    List<Student> listByPage(int pageNum, int pageSize);//分页展示所有学生
    void add(Student student);  //添加学生
    void update(Student student); //修改学生信息
    void delete(long sid); //删除学生
    List<Student> getStudentBySnoList(List<Long> sidList);//根据sno获取学生信息列表
    void UpPaper(List<Answer> answerList);  //提交试卷
}
