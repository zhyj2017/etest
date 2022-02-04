package edu.fzu.etest.service.impl;

import edu.fzu.etest.mapper.StudentMapper;
import edu.fzu.etest.mapper.Student_ClassMapper;
import edu.fzu.etest.service.Student_ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Student_ClassServiceImpl implements Student_ClassService {
    @Autowired
    Student_ClassMapper student_ClassMapper;

}
