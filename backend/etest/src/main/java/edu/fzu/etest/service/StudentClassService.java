package edu.fzu.etest.service;

import edu.fzu.etest.bean.Student;
import edu.fzu.etest.bean.StudentClass;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentClassService {
    //id表示stuid
    List<StudentClass> ShowStuInClass(long id, long classid);
    //添加学生列表到班级
    void AddStuToClass(List<StudentClass> studentClassList);

}
