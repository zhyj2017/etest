package edu.fzu.etest.service;

import edu.fzu.etest.bean.Answer;
import edu.fzu.etest.dto.AnswerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {
    double mark(List<Answer> answers);  //改卷
    List<AnswerDTO> listAnswer(long sid,long tid);  //列出答案
    void saveAnswer(List<Answer> answerList);  //保存答案
}
