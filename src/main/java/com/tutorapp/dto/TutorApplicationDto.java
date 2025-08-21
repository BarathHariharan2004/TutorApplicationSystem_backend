package com.tutorapp.dto;

import com.tutorapp.entity.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class TutorApplicationDto {
    private Long id;

    @NotBlank
    private String title;

    private String description;
    private String subjectExpertise;
    private String teachingMethodology;
    private String preferredGradeLevels;
    private String availabilitySchedule;
    private ApplicationStatus status;
    private String adminComments;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
    
    // Tutor information
    private String tutorName;
    private String tutorEmail;
    private String tutorPhone;

    // Constructors
    public TutorApplicationDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubjectExpertise() { return subjectExpertise; }
    public void setSubjectExpertise(String subjectExpertise) { this.subjectExpertise = subjectExpertise; }

    public String getTeachingMethodology() { return teachingMethodology; }
    public void setTeachingMethodology(String teachingMethodology) { this.teachingMethodology = teachingMethodology; }

    public String getPreferredGradeLevels() { return preferredGradeLevels; }
    public void setPreferredGradeLevels(String preferredGradeLevels) { this.preferredGradeLevels = preferredGradeLevels; }

    public String getAvailabilitySchedule() { return availabilitySchedule; }
    public void setAvailabilitySchedule(String availabilitySchedule) { this.availabilitySchedule = availabilitySchedule; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public String getAdminComments() { return adminComments; }
    public void setAdminComments(String adminComments) { this.adminComments = adminComments; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }

    public String getTutorName() { return tutorName; }
    public void setTutorName(String tutorName) { this.tutorName = tutorName; }

    public String getTutorEmail() { return tutorEmail; }
    public void setTutorEmail(String tutorEmail) { this.tutorEmail = tutorEmail; }

    public String getTutorPhone() { return tutorPhone; }
    public void setTutorPhone(String tutorPhone) { this.tutorPhone = tutorPhone; }
}
