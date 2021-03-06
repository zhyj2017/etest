package edu.fzu.etest.service;

import edu.fzu.etest.bean.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    void add(Question question);//添加问题
    List<Question> getQuestionsByType(long aid, int type); //获取不同类型的问题
    List<Question> getQuestionsByTypeAndPage(long aid, int type, int pageNum, int pageSize); //分页获取不同类型的问题
    List<Question> getQuestionsByPId(long pid);//根据pid获取问题列表
    void update(Question question);//修改问题
    boolean delete(long qid); //删除问题

}
