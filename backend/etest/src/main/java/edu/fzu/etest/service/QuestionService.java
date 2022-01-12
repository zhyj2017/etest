package edu.fzu.etest.service;

import edu.fzu.etest.bean.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    void add(Question question);//添加问题
    List<Question> getQuestionsByType(long aid, int type); //获取不同类型的问题
}
