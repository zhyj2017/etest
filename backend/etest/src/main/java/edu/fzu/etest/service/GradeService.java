package edu.fzu.etest.service;

import edu.fzu.etest.bean.Grade;
import edu.fzu.etest.dto.GradeDTO;
import edu.fzu.etest.dto.StuGradeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeService {
    void add(Grade grade);//添加成绩
    List<Grade> list(long cid, long tid); //查看成绩
    List<GradeDTO> listByCid(long cid, int pageNum, int pageSize);  //管理员查看成绩
    List<StuGradeDTO> listBySid(long sid,int PageNum,int PageSize);  //学生查看成绩
    Long showPaperId(long sid,long tid); //获取pid
    Grade showGrade(long sid,long tid);  //根据sid和tid查询成绩
}
