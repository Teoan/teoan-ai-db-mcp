package com.teoan.teoanaimcpdemo.config;

import com.teoan.teoanaimcpdemo.service.StudentService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Teoan
 * @since 2025/6/11 20:43
 */
@Configuration
public class ToolConfiguration {

    // 注册暴露 MCP Server
    @Bean
    public ToolCallbackProvider weatherTools(StudentService studentService) {
        return MethodToolCallbackProvider.builder().toolObjects(studentService).build();
    }

}
