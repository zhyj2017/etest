package edu.fzu.etest.service;

import edu.fzu.etest.bean.Classes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassesService {
    void AddClass(Classes classes);
    List<Classes> CheckClass(long id);
}
