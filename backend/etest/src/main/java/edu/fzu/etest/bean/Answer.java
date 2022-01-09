package edu.fzu.etest.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Answer {
    @TableId(type = IdType.AUTO)
    private long id;
    private long tid;
    private long pid;
    private long qid;
    private long sid;
    private String answer;
}
