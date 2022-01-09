package edu.fzu.etest.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Paper {
    @TableId(type = IdType.AUTO)
    private long id;
    private String name;
    private double score;
    private String description;
    private long aid;
}
