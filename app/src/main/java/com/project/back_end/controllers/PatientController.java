package com.project.back_end.controllers;

import com.project.back_end.DTO.AppointmentDTO;
import com.project.back_end.DTO.Login;
import com.project.back_end.model.Patient;
import com.project.back_end.service.PatientService;
import com.project.back_end.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for Patient-specific operations.
 * Manages patient authentication, profile updates, and appointment history.
 */
@RestController
@RequestMapping("${api.path}" + "patient")
public class PatientController {

    private final PatientService patientService;
    private final Service service;

    public PatientController(PatientService patientService, Service service) {
        this.patientService = patientService;
        this.service = service;
    }

    /**
     * Authenticates a patient and returns a session token.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login login) {
        Map<String, Object> response = service.login(login, "patient");
        if (response.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Registers a new patient in the system.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Patient patient) {
        patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }

    /**
     * Retrieves the profile details of the logged-in patient.
     */
    @GetMapping("/profile/{email}/{token}")
    public ResponseEntity<Patient> getProfile(@PathVariable String email, @PathVariable String token) {
        Map<String, String> errors = service.validateToken(token, "patient");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(patientService.getPatientByEmail(email));
    }

    /**
     * Fetches the complete appointment history for a patient as DTOs.
     */
    @GetMapping("/appointments/{patientId}/{token}")
    public ResponseEntity<List<AppointmentDTO>> getAppointments(@PathVariable Long patientId, @PathVariable String token) {
        Map<String, String> errors = service.validateToken(token, "patient");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(patientService.getAllAppointments(patientId));
    }

    /**
     * Filters appointments based on status (e.g., 0 for Scheduled, 1 for Completed).
     */
    @GetMapping("/appointments/status/{patientId}/{status}/{token}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByStatus(
            @PathVariable Long patientId, 
            @PathVariable int status, 
            @PathVariable String token) {
        
        Map<String, String> errors = service.validateToken(token, "patient");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(patientService.getAppointmentsByStatus(patientId, status));
    }
}