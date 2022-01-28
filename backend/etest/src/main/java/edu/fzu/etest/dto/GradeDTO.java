package edu.fzu.etest.dto;

import lombok.Data;

@Data
public class GradeDTO {
    private long tid;
    private String tname;
    private long sid;
    private String sno;
    private String sname;
    private double score;
}
