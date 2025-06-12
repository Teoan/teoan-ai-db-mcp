package com.teoan.teoanaimcpdemo.repository;

import com.teoan.teoanaimcpdemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Teoan
 * @since 2025/5/6 11:26
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
    List<Student> findStudentByName(String name);
}
