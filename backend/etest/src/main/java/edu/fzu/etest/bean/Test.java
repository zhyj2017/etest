package edu.fzu.etest.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Test {
    @TableId(type = IdType.AUTO)
    private long id;
    private String tname;
    private Date starttime;
    private Date endtime;
    private String description;
    private long pid;
    private long cid;
    private long aid;
}
