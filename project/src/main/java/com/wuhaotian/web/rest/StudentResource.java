package com.wuhaotian.web.rest;

import com.wuhaotian.domain.User;
import com.wuhaotian.repository.StudentRepository;
import com.wuhaotian.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

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
    public List<User> getAllUser() throws URISyntaxException {
        return userRepository.findAll();
    }
}

