package edu.fzu.etest.service;

import edu.fzu.etest.bean.PaperQuestion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaperQuestionService {
    void add(PaperQuestion paperQuestion);//添加问题到试卷
    void addList(List<PaperQuestion> paperQuestionList);//批量添加
    List<PaperQuestion> getQuestionId(Long pid);//根据试卷pid获取qid列表
    List<PaperQuestion> startExam(long sid, long pid);  //开始考试输出question
}
