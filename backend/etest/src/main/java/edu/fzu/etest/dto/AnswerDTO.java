package edu.fzu.etest.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private long qid;
    private String title;
    private int type;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String analysis;
    private String myAnswer;
}
