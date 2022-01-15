package edu.fzu.etest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.fzu.etest.bean.PaperQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperQuestionMapper extends BaseMapper<PaperQuestion> {
}
