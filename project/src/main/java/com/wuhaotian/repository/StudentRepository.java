package com.wuhaotian.repository;

import com.wuhaotian.domain.Student;
import com.wuhaotian.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link Student} entity.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
