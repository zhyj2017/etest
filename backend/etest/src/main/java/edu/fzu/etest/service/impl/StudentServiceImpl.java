package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Student;
import edu.fzu.etest.mapper.StudentMapper;
import edu.fzu.etest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    public Student getStudentBySno(String sno){
        Student student = studentMapper.selectOne(new QueryWrapper<Student>().eq("sno", sno));
        return student;
    }

    public List<Student> listByPage(int pageNum, int pageSize){
        List<Student> students = new ArrayList<>();
        students = studentMapper.selectPage(new Page<Student>(pageNum,pageSize),null).getRecords();
        return students;
    }

}
