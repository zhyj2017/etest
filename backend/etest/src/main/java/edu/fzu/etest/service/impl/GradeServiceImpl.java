package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Grade;
import edu.fzu.etest.dto.GradeDTO;
import edu.fzu.etest.dto.StuGradeDTO;
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

    public List<Grade> list(long cid, long tid){
        return gradeMapper.selectList(new QueryWrapper<Grade>().eq("cid", cid).eq("tid",tid));
    }

    public List<GradeDTO> listByCid(long cid, int pageNum, int pageSize){
        return gradeMapper.listByCid(new Page<>(pageNum,pageSize),cid).getRecords();
    }
    public List<StuGradeDTO> listBySid(long sid, int pageNum, int pageSize){
        return gradeMapper.listBySid(new Page<>(pageNum,pageSize),sid).getRecords();
    }

    public Long showPaperId(long sid,long tid){
        Grade grade=gradeMapper.selectOne(new QueryWrapper<Grade>().eq("sid",sid).eq("tid",tid));
        return grade.getPid();
    }
    public Grade showGrade(long sid,long tid){
        Grade grade=gradeMapper.selectOne(new QueryWrapper<Grade>().eq("sid",sid).eq("tid",tid));
        return grade;
    }
}
