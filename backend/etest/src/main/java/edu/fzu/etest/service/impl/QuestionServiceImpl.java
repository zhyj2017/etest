package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Question;
import edu.fzu.etest.mapper.QuestionMapper;
import edu.fzu.etest.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    public void add(Question question){
        questionMapper.insert(question);
    }

    public List<Question> getQuestionsByType(long aid, int type){
        return questionMapper.selectList(new QueryWrapper<Question>().eq("type", type).eq("aid",aid));
    }

    public List<Question> getQuestionsByTypeAndPage(long aid, int type, int pageNum, int pageSize){
        return questionMapper.selectPage(new Page<Question>(pageNum,pageSize),new QueryWrapper<Question>().eq("aid",aid).eq("type",type)).getRecords();
    }

    public List<Question> getQuestionById(List<Long> qids){
        List<Question>questionList= questionMapper.selectBatchIds(qids);
        return questionList;
    }
}
