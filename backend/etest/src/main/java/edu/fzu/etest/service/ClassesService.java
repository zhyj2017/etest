package edu.fzu.etest.service;

import edu.fzu.etest.bean.Classes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassesService {
    void addClass(Classes classes);  //添加班级
    List<Classes> checkClass(long aid,long pageNum,long pageSize);  //分页展示班级
    void deleteClass(long cid);  //删除班级
    void updateClass(Classes classes);  //修改班级
    List<Classes> list(long aid);
}
