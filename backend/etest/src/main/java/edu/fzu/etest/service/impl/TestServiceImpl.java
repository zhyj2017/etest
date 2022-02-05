package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.StudentClass;
import edu.fzu.etest.bean.Test;
import edu.fzu.etest.mapper.StudentClassMapper;
import edu.fzu.etest.mapper.StudentMapper;
import edu.fzu.etest.mapper.TestMapper;
import edu.fzu.etest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestMapper testMapper;
    @Autowired
    StudentClassMapper studentClassMapper;

    public void add(Test test){
        testMapper.insert(test);
    }

    public List<Test> list(long aid, int pageNum, int pageSize){
        return testMapper.selectPage(new Page<Test>(pageNum,pageSize),new QueryWrapper<Test>().eq("aid",aid)).getRecords();
    }

    public void update(Test test){
        testMapper.updateById(test);
    }

    public void delete(long qid){
        testMapper.deleteById(qid);
    }

    public List<Test> showTest(long sid, int pageNum, int pageSize){  //检查未完成的考试（时间还未到）
        return testMapper.listTest(new Page<Test>(pageNum,pageSize),sid).getRecords();
    }

    public List<Test> showTestFin(long sid, int pageNum,int pageSize){
        return testMapper.listTestFin(new Page<Test>(pageNum,pageSize),sid).getRecords();
    }

    public Test findByTid(long tid){
        Test test=testMapper.selectOne(new QueryWrapper<Test>().eq("id",tid));
        return test;
    }

    public List<Test> showOvertime(long sid){
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StudentClass studentClass = studentClassMapper.selectOne(new QueryWrapper<StudentClass>().eq("sid",sid));
        List<Test> testList = testMapper.selectList(new QueryWrapper<Test>().eq("cid",studentClass.getCid()).lt("endtime",formatter.format(date)));
        return testList;
    }
}
