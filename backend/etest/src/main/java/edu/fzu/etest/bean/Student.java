package edu.fzu.etest.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Student {
    @TableId(type = IdType.AUTO)
    private long id;
    private String sno;
    private String sname;
    private String sphone;
    private String scollage;
    private String smajor;
    private String password;
}
