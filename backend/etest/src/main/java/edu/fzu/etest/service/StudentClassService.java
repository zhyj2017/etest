package edu.fzu.etest.service;

import edu.fzu.etest.bean.Student;
import edu.fzu.etest.bean.StudentClass;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentClassService {

    void addStuToClass(List<StudentClass> studentClassList); //添加学生列表到班级
    long showClassId(long sid);   //根据学生id查看班级id
    void deleteByCid(long cid);   //根据cid删除班级学生表
    void deleteBySid(long sid);   //根据sid删除学生
    void deleteStuFromClass(long sid,long cid);  //将学生移出班级

}
