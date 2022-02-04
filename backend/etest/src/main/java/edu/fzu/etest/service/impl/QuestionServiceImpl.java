package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Paper;
import edu.fzu.etest.bean.PaperQuestion;
import edu.fzu.etest.bean.Question;
import edu.fzu.etest.mapper.PaperMapper;
import edu.fzu.etest.mapper.PaperQuestionMapper;
import edu.fzu.etest.mapper.QuestionMapper;
import edu.fzu.etest.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    PaperQuestionMapper paperQuestionMapper;

    public void add(Question question){
        questionMapper.insert(question);
    }

    public List<Question> getQuestionsByType(long aid, int type){
        return questionMapper.selectList(new QueryWrapper<Question>().eq("type", type).eq("aid",aid));
    }

    public List<Question> getQuestionsByTypeAndPage(long aid, int type, int pageNum, int pageSize){
        return questionMapper.selectPage(new Page<Question>(pageNum,pageSize),new QueryWrapper<Question>().eq("aid",aid).eq("type",type)).getRecords();
    }

    public Question getQuestionById(long qid){
        Question question= questionMapper.selectOne(new QueryWrapper<Question>().eq("id",qid));
        return question;
    }

    public void update(Question question){
        questionMapper.updateById(question);
    }

    public boolean delete(long qid){
        PaperQuestion paperQuestion = null;
        paperQuestion = paperQuestionMapper.selectOne(new QueryWrapper<PaperQuestion>().eq("qid",qid));
        if (paperQuestion != null){
            return false;
        }
        questionMapper.deleteById(qid);
        return true;
    }
}
