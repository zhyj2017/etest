package edu.fzu.etest.service;

import edu.fzu.etest.bean.Paper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaperService {
    void add(Paper paper);//添加试卷
    List<Paper> list(long id); //获取试卷列表
}
