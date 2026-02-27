package com.project.back_end.controllers;

import com.project.back_end.model.Appointment;
import com.project.back_end.service.AppointmentService;
import com.project.back_end.service.Service;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * Controller for managing appointment-related API endpoints.
 */
@RestController
@RequestMapping("${api.path}" + "appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final Service service;

    public AppointmentController(AppointmentService appointmentService, Service service) {
        this.appointmentService = appointmentService;
        this.service = service;
    }

    /**
     * Retrieves appointments for a specific doctor and date.
     */
    @GetMapping("/{name}/{date}/{token}")
    public ResponseEntity<Map<String, Object>> getAppointment(
            @PathVariable String name,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String token) {
        
        // Validate token for any authenticated user role (admin/doctor/patient)
        if (service.validateToken(token, "admin").isEmpty() || 
            service.validateToken(token, "doctor").isEmpty() || 
            service.validateToken(token, "patient").isEmpty()) {
            return ResponseEntity.ok(appointmentService.getAppointment(name, date, token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Books a new appointment.
     */
    @PostMapping("/{token}")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment, @PathVariable String token) {
        // Patients usually book appointments
        Map<String, String> errors = service.validateToken(token, "patient");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        int result = appointmentService.bookAppointment(appointment);
        return result == 1 
            ? ResponseEntity.status(HttpStatus.CREATED).body("Appointment booked") 
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking failed");
    }

    /**
     * Updates an existing appointment.
     */
    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateAppointment(@RequestBody Appointment appointment, @PathVariable String token) {
        // Both Admin and Patient might need to update an appointment
        if (service.validateToken(token, "admin").isEmpty() || service.validateToken(token, "patient").isEmpty()) {
            return appointmentService.updateAppointment(appointment);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Cancels an appointment.
     */
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> cancelAppointment(@PathVariable long id, @PathVariable String token) {
        if (service.validateToken(token, "admin").isEmpty() || service.validateToken(token, "patient").isEmpty()) {
            return appointmentService.cancelAppointment(id, token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}