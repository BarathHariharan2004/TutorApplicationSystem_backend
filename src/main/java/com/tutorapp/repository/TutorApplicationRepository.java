package com.tutorapp.repository;

import com.tutorapp.entity.ApplicationStatus;
import com.tutorapp.entity.Tutor;
import com.tutorapp.entity.TutorApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TutorApplicationRepository extends JpaRepository<TutorApplication, Long> {
    List<TutorApplication> findByTutor(Tutor tutor);
    List<TutorApplication> findByStatus(ApplicationStatus status);
    List<TutorApplication> findByTutorId(Long tutorId);
    
    @Query("SELECT ta FROM TutorApplication ta WHERE ta.status = :status ORDER BY ta.submittedAt DESC")
    List<TutorApplication> findByStatusOrderBySubmittedAtDesc(@Param("status") ApplicationStatus status);
    
    @Query("SELECT ta FROM TutorApplication ta ORDER BY ta.submittedAt DESC")
    List<TutorApplication> findAllOrderBySubmittedAtDesc();
    
    @Query("SELECT COUNT(ta) FROM TutorApplication ta WHERE ta.status = :status")
    Long countByStatus(@Param("status") ApplicationStatus status);
    
    @Query("SELECT COUNT(ta) FROM TutorApplication ta WHERE ta.submittedAt >= :date")
    Long countApplicationsAfterDate(@Param("date") LocalDateTime date);
    
    @Query("SELECT ta FROM TutorApplication ta WHERE ta.tutor.user.id = :userId")
    List<TutorApplication> findByTutorUserId(@Param("userId") Long userId);
}
