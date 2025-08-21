package com.tutorapp.service;

import com.tutorapp.dto.TutorRegistrationDto;
import com.tutorapp.entity.Tutor;
import com.tutorapp.entity.User;
import com.tutorapp.repository.TutorRepository;
import com.tutorapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerTutor(TutorRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        // Create User
        User user = new User();
        user.setFullName(registrationDto.getFullName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setRole(User.Role.TUTOR);

        User savedUser = userRepository.save(user);

        // Create Tutor profile
        Tutor tutor = new Tutor();
        tutor.setUser(savedUser);
        tutor.setBio(registrationDto.getBio());
        tutor.setYearsOfExperience(registrationDto.getYearsOfExperience());
        tutor.setHourlyRate(registrationDto.getHourlyRate());
        tutor.setQualifications(registrationDto.getQualifications());
        tutor.setSpecializations(registrationDto.getSpecializations());
        tutor.setIsVerified(false);

        tutorRepository.save(tutor);

        return savedUser;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
