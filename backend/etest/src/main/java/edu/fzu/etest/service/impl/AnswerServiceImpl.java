package edu.fzu.etest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.fzu.etest.bean.*;
import edu.fzu.etest.dto.AnswerDTO;
import edu.fzu.etest.mapper.AnswerMapper;
import edu.fzu.etest.mapper.PaperQuestionMapper;
import edu.fzu.etest.mapper.QuestionMapper;
import edu.fzu.etest.mapper.TestMapper;
import edu.fzu.etest.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    PaperQuestionMapper paperQuestionMapper;;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    TestMapper testMapper;

    public double mark(List<Answer> answers){
        double score = 0;
        //第一步，获取卷子上某一题的分值
        //第二步，获取该题的答案
        //第三步，匹配答案，判断是否正确并加分
        //第四步，返回成绩
        for (int i=0;i<answers.size();i++){
            long pid = answers.get(i).getPid();
            long qid = answers.get(i).getQid();
            String answer = answers.get(i).getAnswer();
            PaperQuestion paperQuestion = paperQuestionMapper.selectOne(new QueryWrapper<PaperQuestion>().eq("pid", pid).eq("qid",qid));
            double point = paperQuestion.getPoint();
            Question question = questionMapper.selectOne(new QueryWrapper<Question>().eq("id", qid));
            String c_answer = question.getAnswer();
            if (answer.equals(c_answer)){
                score += point;
            }
        }
        return score;
    }

    public List<AnswerDTO> listAnswer(long sid, long tid) {
        List<AnswerDTO> answerDTOS = answerMapper.listAnswer(tid);
        for (int i = 0; i < answerDTOS.size();i++){
            Answer answer = answerMapper.selectOne(new QueryWrapper<Answer>().eq("qid",answerDTOS.get(i).getQid()).eq("tid",tid).eq("sid",sid));
            if (answer != null){
                answerDTOS.get(i).setMyAnswer(answer.getAnswer());
            }else{
                answerDTOS.get(i).setMyAnswer("");
            }
        }
        return answerDTOS;
    }

    public void saveAnswer(List<Answer> answerList){
        for(int i=0;i<answerList.size();i++){
            answerMapper.insert(answerList.get(i));
        }
    }
}
