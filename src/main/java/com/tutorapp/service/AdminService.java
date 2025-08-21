package com.tutorapp.service;

import com.tutorapp.entity.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TutorService tutorService;

    public Map<String, Object> getDashboardAnalytics() {
        Map<String, Object> analytics = new HashMap<>();

        // Application statistics
        Long totalApplications = applicationService.getApplicationsCountByStatus(ApplicationStatus.PENDING) +
                                applicationService.getApplicationsCountByStatus(ApplicationStatus.APPROVED) +
                                applicationService.getApplicationsCountByStatus(ApplicationStatus.REJECTED);
        
        Long pendingApplications = applicationService.getApplicationsCountByStatus(ApplicationStatus.PENDING);
        Long approvedApplications = applicationService.getApplicationsCountByStatus(ApplicationStatus.APPROVED);
        Long rejectedApplications = applicationService.getApplicationsCountByStatus(ApplicationStatus.REJECTED);

        // Tutor statistics
        Long totalVerifiedTutors = tutorService.getTotalVerifiedTutorsCount();

        // Recent applications (last 30 days)
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        Long recentApplications = applicationService.getRecentApplicationsCount(thirtyDaysAgo);

        analytics.put("totalApplications", totalApplications);
        analytics.put("pendingApplications", pendingApplications);
        analytics.put("approvedApplications", approvedApplications);
        analytics.put("rejectedApplications", rejectedApplications);
        analytics.put("totalVerifiedTutors", totalVerifiedTutors);
        analytics.put("recentApplications", recentApplications);

        return analytics;
    }
}
