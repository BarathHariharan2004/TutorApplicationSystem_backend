package com.tutorapp.repository;

import com.tutorapp.entity.Tutor;
import com.tutorapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByUser(User user);
    Optional<Tutor> findByUserId(Long userId);
    List<Tutor> findByIsVerified(Boolean isVerified);
    
    @Query("SELECT t FROM Tutor t WHERE t.isVerified = true")
    List<Tutor> findAllVerifiedTutors();
    
    @Query("SELECT COUNT(t) FROM Tutor t WHERE t.isVerified = true")
    Long countVerifiedTutors();
}