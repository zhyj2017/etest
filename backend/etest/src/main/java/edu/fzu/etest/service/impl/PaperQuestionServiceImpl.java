package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.fzu.etest.bean.PaperQuestion;
import edu.fzu.etest.mapper.PaperQuestionMapper;
import edu.fzu.etest.service.PaperQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperQuestionServiceImpl implements PaperQuestionService {
    @Autowired
    PaperQuestionMapper paperQuestionMapper;

    public void add(PaperQuestion paperQuestion){
        paperQuestionMapper.insert(paperQuestion);
    }

    public void addList(List<PaperQuestion> paperQuestionList){
        for (int i=0;i<paperQuestionList.size();i++){
            paperQuestionMapper.insert(paperQuestionList.get(i));
        }
    }

    public List<PaperQuestion> getQuestionId(long pid){
        List<PaperQuestion> paperQuestionList=paperQuestionMapper.selectList(new QueryWrapper<PaperQuestion>().eq("pid",pid));
        return paperQuestionList;
    }

}
