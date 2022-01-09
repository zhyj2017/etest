package edu.fzu.etest.service.impl;

import edu.fzu.etest.bean.Paper;
import edu.fzu.etest.mapper.PaperMapper;
import edu.fzu.etest.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    PaperMapper paperMapper;

    public void add(Paper paper){
        paperMapper.insert(paper);
    }

}
