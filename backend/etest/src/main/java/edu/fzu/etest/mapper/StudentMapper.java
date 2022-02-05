package edu.fzu.etest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.fzu.etest.bean.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    IPage<Student> listStudentInClass(Page<Student> page, @Param("cid") long cid);  //根据cid查找班级内的学生
    IPage<Student> listStudentNotInClass(Page<Student> page, @Param("aid") long aid);  //根据aid查找未分班的学生
}
