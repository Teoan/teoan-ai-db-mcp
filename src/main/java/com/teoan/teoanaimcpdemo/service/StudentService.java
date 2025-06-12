package com.teoan.teoanaimcpdemo.service;

import com.teoan.teoanaimcpdemo.entity.Student;
import com.teoan.teoanaimcpdemo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Teoan
 * @since 2025/6/11 20:40
 */
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;


    @Tool(description = "根据学生名称获取学生信息")
    @Transactional(readOnly = true)
    public Student getStudentInfoByName(@ToolParam(description = "学生名称") String  name) {
        List<Student> studentByName = studentRepository.findStudentByName(name);
        return studentByName.getFirst();
    }



    @Tool(description = "获取所有学生信息")
    @Transactional(readOnly = true)
    public List<Student> getAllStudentInfo() {
        return studentRepository.findAll();
    }

}
