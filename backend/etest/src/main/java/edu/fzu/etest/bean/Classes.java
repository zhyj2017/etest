package edu.fzu.etest.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Classes {
    @TableId(type = IdType.AUTO)
    private long id;
    private String name;
    private long aid;
}
