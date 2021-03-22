package com.wuhaotian.web.rest;

import com.wuhaotian.config.Constants;
import com.wuhaotian.domain.User;
import com.wuhaotian.repository.UserRepository;
import com.wuhaotian.security.AuthoritiesConstants;
import com.wuhaotian.service.MailService;
import com.wuhaotian.service.UserService;
import com.wuhaotian.service.dto.UserDTO;
import com.wuhaotian.web.rest.errors.BadRequestAlertException;
import com.wuhaotian.web.rest.errors.EmailAlreadyUsedException;
import com.wuhaotian.web.rest.errors.LoginAlreadyUsedException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentResource {


    private final UserRepository userRepository;


    public StudentResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/all")
    public List<User> getAllUser() throws URISyntaxException {
        return userRepository.findAll();
    }

}

