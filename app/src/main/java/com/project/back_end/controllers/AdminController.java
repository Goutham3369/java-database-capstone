package com.project.back_end.controllers;

import com.project.back_end.DTO.Login;
import com.project.back_end.model.Admin;
import com.project.back_end.model.Doctor;
import com.project.back_end.model.Patient;
import com.project.back_end.service.AuthService;
import com.project.back_end.service.DoctorService;
import com.project.back_end.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.path}" + "admin")
public class AdminController {

    private final AuthService authService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AdminController(AuthService authService, DoctorService doctorService, PatientService patientService) {
        this.authService = authService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login login) {
        Map<String, Object> response = authService.login(login, "admin");
        if (response.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-doctor/{token}")
    public ResponseEntity<Map<String, Object>> addDoctor(@RequestBody Doctor doctor, @PathVariable String token) {
        Map<String, String> errors = authService.validateToken(token, "admin");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(doctorService.saveDoctor(doctor));
    }

    @DeleteMapping("/delete-doctor/{id}/{token}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable long id, @PathVariable String token) {
        Map<String, String> errors = authService.validateToken(token, "admin");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patients/{token}")
    public ResponseEntity<List<Patient>> getPatients(@PathVariable String token) {
        Map<String, String> errors = authService.validateToken(token, "admin");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Assuming a method exists in patientService to get all patients
        return ResponseEntity.ok(null); 
    }
}