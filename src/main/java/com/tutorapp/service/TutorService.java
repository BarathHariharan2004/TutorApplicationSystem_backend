package com.tutorapp.service;

import com.tutorapp.entity.Tutor;
import com.tutorapp.entity.User;
import com.tutorapp.repository.TutorRepository;
import com.tutorapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;
 @Autowired
    private UserRepository userRepository;
    public Tutor findByUser(User user) {
        return tutorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Tutor profile not found for user: " + user.getEmail()));
    }

    public Tutor findById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor not found with id: " + id));
    }

    public List<Tutor> getAllVerifiedTutors() {
        return tutorRepository.findAllVerifiedTutors();
    }

    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    public Tutor updateTutor(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    public void verifyTutor(Long tutorId) {
        Tutor tutor = findById(tutorId);
        tutor.setIsVerified(true);
        tutorRepository.save(tutor);
    }

    public Long getTotalVerifiedTutorsCount() {
        return tutorRepository.countVerifiedTutors();
    }

    public void deleteTutorById(Long id) {
  Tutor tutor = tutorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tutor not found with id: " + id));
        User user = tutor.getUser();
        tutorRepository.deleteById(id);
        if (user != null) {
            userRepository.deleteById(user.getId());
        }}
}
