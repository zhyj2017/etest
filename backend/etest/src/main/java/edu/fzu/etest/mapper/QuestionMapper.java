package edu.fzu.etest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.fzu.etest.bean.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
