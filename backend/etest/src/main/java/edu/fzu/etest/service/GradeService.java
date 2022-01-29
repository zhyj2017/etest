package edu.fzu.etest.service;

import edu.fzu.etest.bean.Grade;
import edu.fzu.etest.dto.GradeDTO;
import edu.fzu.etest.dto.StuGradeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeService {
    void add(Grade grade);//添加问题
    List<Grade> list(long cid, long tid); //查看成绩
    List<GradeDTO> listByPage(long cid, int pageNum, int pageSize);  //管理员查看成绩
}
