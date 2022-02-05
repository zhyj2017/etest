package edu.fzu.etest.service;

import edu.fzu.etest.bean.Test;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService {
    void add(Test test);  //添加考试
    List<Test> list(long aid, int pageNum, int pageSize); //查看考试列表
    void update(Test test);  //修改考试
    void delete(long tid);  //删除考试
    List<Test> showTest(long sid, int pageNum, int pageSize);  //查看未完成的考试
    List<Test> showTestFin(long sid, int pageNum, int pageSize);  //查看以及完成的考试
    Test findByTid(long tid);  //根据tid查询Test
    List<Test> showOvertime(long sid);  //查看学生超时的考试

}
