package com.project.back_end.controllers;

import com.project.back_end.DTO.Login;
import com.project.back_end.model.Admin;
import com.project.back_end.model.Doctor;
import com.project.back_end.model.Patient;
import com.project.back_end.service.DoctorService;
import com.project.back_end.service.PatientService;
import com.project.back_end.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for Administrative operations.
 * Manages the high-level control of Doctors, Patients, and Admin login.
 */
@RestController
@RequestMapping("${api.path}" + "admin")
public class AdminController {

    private final Service service;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AdminController(Service service, DoctorService doctorService, PatientService patientService) {
        this.service = service;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login login) {
        Map<String, Object> response = service.login(login, "admin");
        if (response.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-doctor/{token}")
    public ResponseEntity<Map<String, Object>> addDoctor(@RequestBody Doctor doctor, @PathVariable String token) {
        Map<String, String> errors = service.validateToken(token, "admin");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(doctorService.saveDoctor(doctor));
    }

    @DeleteMapping("/delete-doctor/{id}/{token}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable long id, @PathVariable String token) {
        Map<String, String> errors = service.validateToken(token, "admin");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patients/{token}")
    public ResponseEntity<List<Patient>> getPatients(@PathVariable String token) {
        // Validation logic can be added here if needed
        return ResponseEntity.ok(null); // Placeholder for patient list logic
    }
}