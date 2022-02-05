package edu.fzu.etest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper extends BaseMapper<Test> {
    IPage<Test> listTest(Page<Test> page, @Param("sid") long sid);     //查看未完成考试
    IPage<Test> listTestFin(Page<Test> page, @Param("sid") long sid);  //查看已完成考试
}
