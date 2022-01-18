package edu.fzu.etest.service;

import edu.fzu.etest.bean.Grade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeService {
    void add(Grade grade);//添加问题
    List<Grade> list(Grade grade); //查看成绩
}
