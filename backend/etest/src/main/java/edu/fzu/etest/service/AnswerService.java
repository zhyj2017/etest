package edu.fzu.etest.service;

import edu.fzu.etest.bean.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {
    double mark(List<Answer> answers);  //改卷
}
