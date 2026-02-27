package com.project.back_end.controllers;

import com.project.back_end.DTO.AppointmentDTO;
import com.project.back_end.DTO.Login;
import com.project.back_end.model.Patient;
import com.project.back_end.service.AuthService;
import com.project.back_end.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.path}" + "patient")
public class PatientController {

    private final PatientService patientService;
    private final AuthService authService;

    public PatientController(PatientService patientService, AuthService authService) {
        this.patientService = patientService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login login) {
        Map<String, Object> response = authService.login(login, "patient");
        if (response.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Patient patient) {
        patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }

    @GetMapping("/profile/{email}/{token}")
    public ResponseEntity<Patient> getProfile(@PathVariable String email, @PathVariable String token) {
        Map<String, String> errors = authService.validateToken(token, "patient");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(patientService.getPatientByEmail(email));
    }

    @GetMapping("/appointments/{patientId}/{token}")
    public ResponseEntity<List<AppointmentDTO>> getAppointments(@PathVariable Long patientId, @PathVariable String token) {
        Map<String, String> errors = authService.validateToken(token, "patient");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(patientService.getAllAppointments(patientId));
    }

    @GetMapping("/appointments/status/{patientId}/{status}/{token}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByStatus(
            @PathVariable Long patientId, 
            @PathVariable int status, 
            @PathVariable String token) {
        
        Map<String, String> errors = authService.validateToken(token, "patient");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(patientService.getAppointmentsByStatus(patientId, status));
    }
}