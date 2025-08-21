package com.tutorapp.repository;

import com.tutorapp.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);
    List<Subject> findByGradeLevel(String gradeLevel);
    Boolean existsByName(String name);
    
    @Query("SELECT s FROM Subject s ORDER BY s.name")
    List<Subject> findAllOrderByName();
}