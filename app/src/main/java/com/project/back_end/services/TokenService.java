package com.project.back_end.service;

import com.project.back_end.repo.AdminRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

/**
 * TokenService handles the creation and validation of session tokens.
 */
@Service
public class TokenService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public TokenService(AdminRepository adminRepository, 
                        DoctorRepository doctorRepository, 
                        PatientRepository patientRepository) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Creates a simple Base64 encoded token containing the user ID and role.
     */
    public String createToken(Long id, String role) {
        String rawToken = id + ":" + role + ":" + UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString(rawToken.getBytes());
    }

    /**
     * Validates if a token is authentic and matches the required role.
     */
    public boolean validateToken(String token, String requiredRole) {
        try {
            if (token == null || token.isEmpty()) return false;

            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split(":");
            
            if (parts.length < 2) return false;

            Long id = Long.parseLong(parts[0]);
            String role = parts[1];

            if (!role.equalsIgnoreCase(requiredRole)) return false;

            // Verify the user actually exists in the database
            return switch (role.toLowerCase()) {
                case "admin" -> adminRepository.existsById(id);
                case "doctor" -> doctorRepository.existsById(id);
                case "patient" -> patientRepository.existsById(id);
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }
}