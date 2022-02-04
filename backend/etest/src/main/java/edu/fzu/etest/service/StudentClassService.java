package edu.fzu.etest.service;

import edu.fzu.etest.bean.Student;
import edu.fzu.etest.bean.StudentClass;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentClassService {

    //添加学生列表到班级
    void addStuToClass(List<StudentClass> studentClassList);
    //根据学生id查看班级id
    long showClassId(long sid);
    //根据cid删除班级学生表
    void deleteByCid(long cid);
    //根据sid删除学生
    void deleteBySid(long sid);
    //学生退出班级
    void deleteStuFromClass(long sid,long cid);

}
