package com.tutorapp.dto;

import com.tutorapp.entity.Tutor;
import com.tutorapp.entity.User;

public class TutorProfileDto {
    private Long id;
    private String bio;
    private Integer yearsOfExperience;
    private Double hourlyRate;
    private String qualifications;
    private String specializations;
    private String profilePictureUrl;
    private Boolean isVerified;
    private String fullName;
    private String email;
    private String phoneNumber;

    public TutorProfileDto(Tutor tutor) {
        this.id = tutor.getId();
        this.bio = tutor.getBio();
        this.yearsOfExperience = tutor.getYearsOfExperience();
        this.hourlyRate = tutor.getHourlyRate();
        this.qualifications = tutor.getQualifications();
        this.specializations = tutor.getSpecializations();
        this.profilePictureUrl = tutor.getProfilePictureUrl();
        this.isVerified = tutor.getIsVerified();
        this.phoneNumber = tutor.getUser().getPhoneNumber(); // ✅ get from user entity


        User user = tutor.getUser();
        if (user != null) {
            this.fullName = user.getFullName();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getBio() { return bio; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public Double getHourlyRate() { return hourlyRate; }
    public String getQualifications() { return qualifications; }
    public String getSpecializations() { return specializations; }
    public String getProfilePictureUrl() { return profilePictureUrl; }
    public Boolean getIsVerified() { return isVerified; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
}
