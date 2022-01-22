package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Classes;
import edu.fzu.etest.bean.Question;
import edu.fzu.etest.mapper.ClassesMapper;
import edu.fzu.etest.mapper.GradeMapper;
import edu.fzu.etest.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    ClassesMapper classesMapper;

    public void AddClass(Classes classes)
    {
         classesMapper.insert(classes);
    }
    public List<Classes> CheckClass(long id){
        return classesMapper.selectList(new QueryWrapper<Classes>().eq("id",id));
    }
}
