package edu.fzu.etest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Grade;
import edu.fzu.etest.dto.GradeDTO;
import edu.fzu.etest.dto.StuGradeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GradeMapper extends BaseMapper<Grade> {
    IPage<GradeDTO> listByCid(Page<Grade> page, @Param("cid") long cid);  //根据cid查看考试成绩
    IPage<StuGradeDTO> listBySid(Page<Grade> page, @Param("sid") long sid);  //根据sid查看成绩
}
