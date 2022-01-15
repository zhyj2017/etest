package edu.fzu.etest.service.impl;

import edu.fzu.etest.bean.Test;
import edu.fzu.etest.mapper.TestMapper;
import edu.fzu.etest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestMapper testMapper;

    public void add(Test test){
        testMapper.insert(test);
    }
}
