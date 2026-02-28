package com.project.back_end.controllers;

import com.project.back_end.DTO.Login;
import com.project.back_end.model.Appointment;
import com.project.back_end.model.Doctor;
import com.project.back_end.service.AuthService;
import com.project.back_end.service.DoctorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.path}" + "doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final AuthService authService;

    public DoctorController(DoctorService doctorService, AuthService authService) {
        this.doctorService = doctorService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login login) {
        Map<String, Object> response = authService.login(login, "doctor");
        if (response.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/appointments/{id}/{start}/{end}/{token}")
    public ResponseEntity<List<Appointment>> getAppointments(
            @PathVariable Long id,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @PathVariable String token) {
        
        Map<String, String> errors = authService.validateToken(token, "doctor");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        return ResponseEntity.ok(doctorService.getAppointments(id, start, end));
    }

    @GetMapping("/profile/{email}/{token}")
    public ResponseEntity<Doctor> getProfile(@PathVariable String email, @PathVariable String token) {
        if (authService.validateToken(token, "admin").isEmpty() || authService.validateToken(token, "doctor").isEmpty()) {
            return ResponseEntity.ok(doctorService.getDoctorByEmail(email));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialty(@PathVariable String specialty) {
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialty(specialty));
    }

    /**
     * Requirement for Question 5: Retrieve doctor availability based on role, ID, date, and token.
     */
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<List<Object>> getAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable String date,
            @PathVariable String token) {
        
        // Security check for either doctor or patient roles
        if (authService.validateToken(token, user).isEmpty()) {
            return ResponseEntity.ok(doctorService.getAvailability(doctorId, date));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
}
