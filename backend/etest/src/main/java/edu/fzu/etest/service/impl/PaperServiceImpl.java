package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Paper;
import edu.fzu.etest.mapper.PaperMapper;
import edu.fzu.etest.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    PaperMapper paperMapper;

    public void add(Paper paper){
        paperMapper.insert(paper);
    }

    public List<Paper> list(long aid){
        return paperMapper.selectList(new QueryWrapper<Paper>().eq("aid",aid));
    }

    public List<Paper> listByPage(long aid, int pageNum, int pageSize){
        return paperMapper.selectPage(new Page<Paper>(pageNum,pageSize),new QueryWrapper<Paper>().eq("aid",aid)).getRecords();
    }
}
