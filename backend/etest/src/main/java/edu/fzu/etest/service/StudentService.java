package edu.fzu.etest.service;

import edu.fzu.etest.bean.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student getStudentBySno(String sno);  //根据sno获得学生
    List<Student> listByPage(int pageNum, int pageSize);//分页展示所有学生
}
