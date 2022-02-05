package edu.fzu.etest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Answer;
import edu.fzu.etest.bean.Grade;
import edu.fzu.etest.dto.AnswerDTO;
import edu.fzu.etest.dto.GradeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {
    List<AnswerDTO> listAnswer(@Param("tid") long tid);  //根据tid查看正确答案
}
