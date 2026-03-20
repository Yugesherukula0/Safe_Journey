package com.yugesh.safejourney.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yugesh.safejourney.dto.LoginRequest;
import com.yugesh.safejourney.dto.OtpRequest;
import com.yugesh.safejourney.dto.OtpVerifyRequest;
import com.yugesh.safejourney.dto.RegisterRequest;
import com.yugesh.safejourney.entities.OtpVerification;
import com.yugesh.safejourney.entities.User;
import com.yugesh.safejourney.repository.OtpRepository;
import com.yugesh.safejourney.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    // REGISTER
    public String register(RegisterRequest request) {

        // check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        
        // check if phone number already exists
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already registered");
        }

        // create user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();
        
        userRepository.save(user);

        return "User registered successfully";
    }

    // LOGIN
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        return "Login successful";
    }
    
    public String sendOtp(OtpRequest request) {

        String email = request.getEmail();

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        otpRepository.deleteByEmail(email);

        OtpVerification otpEntity = OtpVerification.builder()
                .email(email)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();

        otpRepository.save(otpEntity);

        // 🔥 SEND EMAIL HERE
        emailService.sendOtpEmail(email, otp);

        return "OTP sent successfully";
    }
    public String verifyOtp(OtpVerifyRequest request) {

        OtpVerification otpData = otpRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otpData.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otpData.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        otpRepository.deleteByEmail(request.getEmail());

        return "Login successful via OTP";
    }
}