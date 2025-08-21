package com.tutorapp.service;

import com.tutorapp.dto.TutorApplicationDto;
import com.tutorapp.entity.ApplicationStatus;
import com.tutorapp.entity.Tutor;
import com.tutorapp.entity.TutorApplication;
import com.tutorapp.entity.User;
import com.tutorapp.repository.TutorApplicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationService {

    @Autowired
    private TutorApplicationRepository applicationRepository;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private ModelMapper modelMapper;

    public TutorApplication submitApplication(TutorApplicationDto applicationDto, User currentUser) {
        Tutor tutor = tutorService.findByUser(currentUser);

        TutorApplication application = new TutorApplication();
        application.setTutor(tutor);
        application.setTitle(applicationDto.getTitle());
        application.setDescription(applicationDto.getDescription());
        application.setSubjectExpertise(applicationDto.getSubjectExpertise());
        application.setTeachingMethodology(applicationDto.getTeachingMethodology());
        application.setPreferredGradeLevels(applicationDto.getPreferredGradeLevels());
        application.setAvailabilitySchedule(applicationDto.getAvailabilitySchedule());
        application.setStatus(ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    public List<TutorApplicationDto> getTutorApplications(User currentUser) {
        List<TutorApplication> applications = applicationRepository.findByTutorUserId(currentUser.getId());
        return applications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TutorApplicationDto> getAllApplications() {
        List<TutorApplication> applications = applicationRepository.findAllOrderBySubmittedAtDesc();
        return applications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TutorApplicationDto> getApplicationsByStatus(ApplicationStatus status) {
        List<TutorApplication> applications = applicationRepository.findByStatusOrderBySubmittedAtDesc(status);
        return applications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TutorApplication approveApplication(Long applicationId, String adminComments, User reviewer) {
        TutorApplication application = findById(applicationId);
        application.setStatus(ApplicationStatus.APPROVED);
        application.setAdminComments(adminComments);
        application.setReviewedBy(reviewer);
        application.setReviewedAt(LocalDateTime.now());

        // Verify the tutor when application is approved
        tutorService.verifyTutor(application.getTutor().getId());

        return applicationRepository.save(application);
    }

    public TutorApplication rejectApplication(Long applicationId, String adminComments, User reviewer) {
        TutorApplication application = findById(applicationId);
        application.setStatus(ApplicationStatus.REJECTED);
        application.setAdminComments(adminComments);
        application.setReviewedBy(reviewer);
        application.setReviewedAt(LocalDateTime.now());

        return applicationRepository.save(application);
    }

    public TutorApplication findById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
    }

    public Long getApplicationsCountByStatus(ApplicationStatus status) {
        return applicationRepository.countByStatus(status);
    }

    public Long getRecentApplicationsCount(LocalDateTime since) {
        return applicationRepository.countApplicationsAfterDate(since);
    }

    private TutorApplicationDto convertToDto(TutorApplication application) {
        TutorApplicationDto dto = modelMapper.map(application, TutorApplicationDto.class);
        dto.setTutorName(application.getTutor().getUser().getFullName());
        dto.setTutorEmail(application.getTutor().getUser().getEmail());
        dto.setTutorPhone(application.getTutor().getUser().getPhoneNumber());
        return dto;
    }
}