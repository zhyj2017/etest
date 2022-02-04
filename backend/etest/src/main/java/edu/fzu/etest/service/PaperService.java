package edu.fzu.etest.service;

import edu.fzu.etest.bean.Answer;
import edu.fzu.etest.bean.Paper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaperService {
    void add(Paper paper);//添加试卷
    List<Paper> list(long aid); //获取试卷列表
    List<Paper> listByPage(long aid, int pageNum, int pageSize);  //分页获取试卷列表
    void update(Paper paper);//修改试卷
    boolean delete(long pid); //删除试卷
    void UpPaper(List<Answer> answerList);  //提交试卷
}
