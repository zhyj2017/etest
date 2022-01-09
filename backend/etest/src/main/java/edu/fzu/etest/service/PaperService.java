package edu.fzu.etest.service;

import edu.fzu.etest.bean.Paper;
import org.springframework.stereotype.Service;

@Service
public interface PaperService {
    void add(Paper paper);//添加试卷
}
