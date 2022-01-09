package edu.fzu.etest.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Grade {
    @TableId(type = IdType.AUTO)
    private long id;
    private long tid;
    private long pid;
    private long sid;
    private long cid;
    private double score;
}
