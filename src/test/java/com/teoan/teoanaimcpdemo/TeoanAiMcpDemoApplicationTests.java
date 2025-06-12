package com.teoan.teoanaimcpdemo;

import com.teoan.teoanaimcpdemo.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TeoanAiMcpDemoApplicationTests {

    @Autowired
    StudentService studentService;


    @Test
    void contextLoads() {

    }

}
