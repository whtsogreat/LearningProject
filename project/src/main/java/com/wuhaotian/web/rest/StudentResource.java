package com.wuhaotian.web.rest;

import com.wuhaotian.domain.Student;
import com.wuhaotian.repository.StudentRepository;
import com.wuhaotian.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentResource {

    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    public StudentResource(UserRepository userRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/all")
    public List<Student> getAllUser() throws URISyntaxException {
        return studentRepository.findAll();
    }
}

