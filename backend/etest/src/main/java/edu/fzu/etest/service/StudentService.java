package edu.fzu.etest.service;

import edu.fzu.etest.bean.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student getStudentBySno(String sno);  //根据sno获得学生
    List<Student> listByPage(int pageNum, int pageSize);//分页展示所有学生
    void update(Student student); //修改学生信息
    List<Student> getStudentBySnoList(List<Long> sidList);//根据sno获取学生信息列表
    List<Test> showPaper(long id);  //查看未完成的考试
    List<PaperQuestion> startExam(long sid, long pid);  //开始考试输出question
    void UpPaper(List<Answer> answerList);  //提交试卷
    Double showScore(Long sid,Long paperid);
}
