package edu.fzu.etest.service;

import edu.fzu.etest.bean.Test;
import org.springframework.stereotype.Service;

@Service
public interface TestService {
    long add(Test test);  //添加考试

}
