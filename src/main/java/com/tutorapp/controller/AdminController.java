package com.tutorapp.controller;

import com.tutorapp.dto.AdminTutorDto;
import com.tutorapp.dto.ApiResponse;
import com.tutorapp.entity.ApplicationStatus;
import com.tutorapp.entity.Tutor;
import com.tutorapp.entity.User;
import com.tutorapp.service.AdminService;
import com.tutorapp.service.ApplicationService;
import com.tutorapp.service.CustomUserDetailsService;
import com.tutorapp.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private TutorService tutorService;

    @GetMapping("/applications")
    public ResponseEntity<?> getAllApplications(@RequestParam(required = false) String status) {
        try {
            if (status != null && !status.isEmpty()) {
                ApplicationStatus appStatus = ApplicationStatus.valueOf(status.toUpperCase());
                return ResponseEntity.ok(new ApiResponse(true, "Applications retrieved successfully", 
                        applicationService.getApplicationsByStatus(appStatus)));
            } else {
                return ResponseEntity.ok(new ApiResponse(true, "Applications retrieved successfully", 
                        applicationService.getAllApplications()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/applications/{id}/approve")
    public ResponseEntity<?> approveApplication(@PathVariable Long id, 
                                              @RequestBody(required = false) Map<String, String> request) {
        try {
            User currentUser = getCurrentUser();
            String comments = request != null ? request.get("comments") : "";
            applicationService.approveApplication(id, comments, currentUser);
            return ResponseEntity.ok(new ApiResponse(true, "Application approved successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/applications/{id}/reject")
    public ResponseEntity<?> rejectApplication(@PathVariable Long id, 
                                             @RequestBody(required = false) Map<String, String> request) {
        try {
            User currentUser = getCurrentUser();
            String comments = request != null ? request.get("comments") : "";
            applicationService.rejectApplication(id, comments, currentUser);
            return ResponseEntity.ok(new ApiResponse(true, "Application rejected successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/analytics")
    public ResponseEntity<?> getDashboardAnalytics() {
        try {
            Map<String, Object> analytics = adminService.getDashboardAnalytics();
            return ResponseEntity.ok(new ApiResponse(true, "Analytics retrieved successfully", analytics));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

   @GetMapping("/tutors")
public ResponseEntity<?> getAllTutors() {
    List<Tutor> tutors = tutorService.getAllTutors();
    List<AdminTutorDto> dtos = tutors.stream()
        .map(AdminTutorDto::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
}
@DeleteMapping("/tutors/{id}")
public ResponseEntity<?> deleteTutor(@PathVariable Long id) {
    try {
        tutorService.deleteTutorById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Tutor deleted successfully"));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
    }
}
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService.CustomUserPrincipal userPrincipal = 
                (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser();
    }
}
