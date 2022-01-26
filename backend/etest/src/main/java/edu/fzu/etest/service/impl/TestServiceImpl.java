package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Test;
import edu.fzu.etest.mapper.TestMapper;
import edu.fzu.etest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestMapper testMapper;

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
}
