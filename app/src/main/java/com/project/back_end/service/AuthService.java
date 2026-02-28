package com.project.back_end.service;

import com.project.back_end.DTO.Login;
import com.project.back_end.model.Admin;
import com.project.back_end.model.Doctor;
import com.project.back_end.model.Patient;
import com.project.back_end.repo.AdminRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Authentication service for validating logins, tokens, and registration.
 */
@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final TokenService tokenService;

    public AuthService(AdminRepository adminRepository, 
                       DoctorRepository doctorRepository, 
                       PatientRepository patientRepository, 
                       TokenService tokenService) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.tokenService = tokenService;
    }

    /**
     * ADDED: registerAdmin method to solve the 'cannot find symbol' error.
     * This allows the AdminController to save a new admin to the MySQL database.
     */
    public void registerAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public Map<String, Object> login(Login login, String role) {
        Map<String, Object> response = new HashMap<>();
        String token = "";

        if ("admin".equalsIgnoreCase(role)) {
            Admin admin = adminRepository.findByUsername(login.getUsername());
            if (admin != null && admin.getPassword().equals(login.getPassword())) {
                token = tokenService.createToken(admin.getId(), "admin");
                response.put("admin", admin);
            }
        } else if ("doctor".equalsIgnoreCase(role)) {
            Doctor doctor = doctorRepository.findByEmail(login.getUsername());
            if (doctor != null && doctor.getPassword().equals(login.getPassword())) {
                token = tokenService.createToken(doctor.getId(), "doctor");
                response.put("doctor", doctor);
            }
        } else if ("patient".equalsIgnoreCase(role)) {
            Patient patient = patientRepository.findByEmail(login.getUsername());
            if (patient != null && patient.getPassword().equals(login.getPassword())) {
                token = tokenService.createToken(patient.getId(), "patient");
                response.put("patient", patient);
            }
        }

        if (!token.isEmpty()) {
            response.put("token", token);
        } else {
            response.put("error", "Invalid credentials or role");
        }
        return response;
    }

    public Map<String, String> validateToken(String token, String requiredRole) {
        Map<String, String> errors = new HashMap<>();
        if (!tokenService.validateToken(token, requiredRole)) {
            errors.put("auth", "Unauthorized access");
        }
        return errors;
    }
}