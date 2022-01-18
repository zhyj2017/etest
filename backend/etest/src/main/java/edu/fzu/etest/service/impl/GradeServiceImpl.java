package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.fzu.etest.bean.Grade;
import edu.fzu.etest.mapper.GradeMapper;
import edu.fzu.etest.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    GradeMapper gradeMapper;

    public void add(Grade grade){
        gradeMapper.insert(grade);
    }

    public List<Grade> list(Grade grade){
        return gradeMapper.selectList(new QueryWrapper<Grade>().eq("cid", grade.getCid()).eq("pid",grade.getPid()));
    }

}
