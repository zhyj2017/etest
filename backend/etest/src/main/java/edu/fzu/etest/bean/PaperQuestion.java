package edu.fzu.etest.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PaperQuestion {
    @TableId(type = IdType.AUTO)
    private long id;
    private long pid;
    private long qid;
    private double point;
}
