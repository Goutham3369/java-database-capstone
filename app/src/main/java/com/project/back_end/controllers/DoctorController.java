package com.project.back_end.controllers;

import com.project.back_end.DTO.Login;
import com.project.back_end.model.Appointment;
import com.project.back_end.model.Doctor;
import com.project.back_end.service.DoctorService;
import com.project.back_end.service.Service;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controller for Doctor-specific operations.
 * Handles doctor login, profile retrieval, and appointment scheduling.
 */
@RestController
@RequestMapping("${api.path}" + "doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final Service service;

    public DoctorController(DoctorService doctorService, Service service) {
        this.doctorService = doctorService;
        this.service = service;
    }

    /**
     * Authenticates a doctor and returns a session token.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login login) {
        Map<String, Object> response = service.login(login, "doctor");
        if (response.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the list of appointments for the logged-in doctor within a timeframe.
     */
    @GetMapping("/appointments/{id}/{start}/{end}/{token}")
    public ResponseEntity<List<Appointment>> getAppointments(
            @PathVariable Long id,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @PathVariable String token) {
        
        Map<String, String> errors = service.validateToken(token, "doctor");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        return ResponseEntity.ok(doctorService.getAppointments(id, start, end));
    }

    /**
     * Fetches the profile details of a doctor using their email.
     */
    @GetMapping("/profile/{email}/{token}")
    public ResponseEntity<Doctor> getProfile(@PathVariable String email, @PathVariable String token) {
        // Validation for either Admin or the Doctor themselves
        if (service.validateToken(token, "admin").isEmpty() || service.validateToken(token, "doctor").isEmpty()) {
            return ResponseEntity.ok(doctorService.getDoctorByEmail(email));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Retrieves a list of doctors filtered by their specialty.
     */
    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialty(@PathVariable String specialty) {
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialty(specialty));
    }
}