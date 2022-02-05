package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.*;
import edu.fzu.etest.mapper.StudentClassMapper;
import edu.fzu.etest.mapper.StudentMapper;
import edu.fzu.etest.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentClassServiceImpl implements StudentClassService {
    @Autowired
    StudentClassMapper studentClassMapper;

    public void addStuToClass(List<StudentClass> studentClassList){
        for(int i=0;i<studentClassList.size();i++){
            studentClassMapper.insert(studentClassList.get(i));
        }
    }

    public long showClassId(long sid){
        StudentClass studentClass=studentClassMapper.selectOne(new QueryWrapper<StudentClass>().eq("sid",sid));
        return studentClass.getCid();
    }
    public void deleteByCid(long cid){
        studentClassMapper.delete(new QueryWrapper<StudentClass>().eq("cid",cid));
    }

    public void deleteBySid(long sid){
        studentClassMapper.delete(new QueryWrapper<StudentClass>().eq("sid",sid));
    }

    public void deleteStuFromClass(long sid,long cid){
        studentClassMapper.delete(new QueryWrapper<StudentClass>().eq("sid",sid).eq("cid",cid));
    }

}
