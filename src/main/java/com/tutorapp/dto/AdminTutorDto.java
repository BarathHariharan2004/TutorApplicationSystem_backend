package com.tutorapp.dto;

import com.tutorapp.entity.Tutor;
import com.tutorapp.entity.User;

public class AdminTutorDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String bio;
    private Integer yearsOfExperience;
    private Double hourlyRate;
    private String qualifications;
    private String specializations;
    private Boolean isVerified;
    private String createdAt;
    private int applicationsCount;

    public AdminTutorDto(Tutor tutor) {
        this.id = tutor.getId();
        User user = tutor.getUser();
        if (user != null) {
            this.fullName = user.getFullName();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
        }
        this.bio = tutor.getBio();
        this.yearsOfExperience = tutor.getYearsOfExperience();
        this.hourlyRate = tutor.getHourlyRate();
        this.qualifications = tutor.getQualifications();
        this.specializations = tutor.getSpecializations();
        this.isVerified = tutor.getIsVerified();
        this.createdAt = tutor.getCreatedAt() != null ? tutor.getCreatedAt().toString() : null;
        this.applicationsCount = tutor.getApplications() != null ? tutor.getApplications().size() : 0;
    }

    // Getters (add setters if needed)
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getBio() { return bio; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public Double getHourlyRate() { return hourlyRate; }
    public String getQualifications() { return qualifications; }
    public String getSpecializations() { return specializations; }
    public Boolean getIsVerified() { return isVerified; }
    public String getCreatedAt() { return createdAt; }
    public int getApplicationsCount() { return applicationsCount; }
}