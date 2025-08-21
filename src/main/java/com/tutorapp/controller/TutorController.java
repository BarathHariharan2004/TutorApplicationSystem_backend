package com.tutorapp.controller;

import com.tutorapp.dto.ApiResponse;
import com.tutorapp.dto.TutorApplicationDto;
import com.tutorapp.dto.TutorProfileDto;
import com.tutorapp.entity.Tutor;
import com.tutorapp.entity.User;
import com.tutorapp.service.ApplicationService;
import com.tutorapp.service.CustomUserDetailsService;
import com.tutorapp.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TutorService tutorService;

    @GetMapping("/profile")
    public ResponseEntity<?> getTutorProfile() {
        try {
            User currentUser = getCurrentUser();
            Tutor tutor = tutorService.findByUser(currentUser);
            if (tutor == null) {
                return ResponseEntity.badRequest().body("No profile found for user");
            }
            TutorProfileDto dto = new TutorProfileDto(tutor);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Add this: Update the logged-in tutor's profile
    @PutMapping("/profile")
    public ResponseEntity<?> updateTutorProfile(@Valid @RequestBody Tutor updatedTutor) {
        try {
            User currentUser = getCurrentUser();
            Tutor existingTutor = tutorService.findByUser(currentUser);

            // Update only allowed fields (null-safe)
            if (updatedTutor.getBio() != null) {
                existingTutor.setBio(updatedTutor.getBio());
            }
            if (updatedTutor.getYearsOfExperience() != null) {
                existingTutor.setYearsOfExperience(updatedTutor.getYearsOfExperience());
            }
            if (updatedTutor.getHourlyRate() != null) {
                existingTutor.setHourlyRate(updatedTutor.getHourlyRate());
            }
            if (updatedTutor.getQualifications() != null) {
                existingTutor.setQualifications(updatedTutor.getQualifications());
            }
            if (updatedTutor.getSpecializations() != null) {
                existingTutor.setSpecializations(updatedTutor.getSpecializations());
            }
            if (updatedTutor.getProfilePictureUrl() != null) {
                existingTutor.setProfilePictureUrl(updatedTutor.getProfilePictureUrl());
            }

            Tutor savedTutor = tutorService.updateTutor(existingTutor);

            return ResponseEntity.ok(new ApiResponse(true, "Profile updated successfully", savedTutor));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/applications")
    public ResponseEntity<?> submitApplication(@Valid @RequestBody TutorApplicationDto applicationDto) {
        try {
            User currentUser = getCurrentUser();
            applicationService.submitApplication(applicationDto, currentUser);
            return ResponseEntity.ok(new ApiResponse(true, "Application submitted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/applications")
    public ResponseEntity<?> getMyApplications() {
        try {
            User currentUser = getCurrentUser();
            List<TutorApplicationDto> applications = applicationService.getTutorApplications(currentUser);
            return ResponseEntity.ok(new ApiResponse(true, "Applications retrieved successfully", applications));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService.CustomUserPrincipal userPrincipal =
                (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser();
    }
}
