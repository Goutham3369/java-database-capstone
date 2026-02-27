package com.project.back_end.controllers;

import com.project.back_end.model.Prescription;
import com.project.back_end.service.AppointmentService;
import com.project.back_end.service.PrescriptionService;
import com.project.back_end.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for handling Prescription-related medical data.
 */
@RestController
@RequestMapping("${api.path}" + "prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final Service service;
    private final AppointmentService appointmentService;

    public PrescriptionController(PrescriptionService prescriptionService, 
                                  Service service, 
                                  AppointmentService appointmentService) {
        this.prescriptionService = prescriptionService;
        this.service = service;
        this.appointmentService = appointmentService;
    }

    /**
     * Saves a new prescription and automatically marks the appointment as 'Completed'.
     */
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> savePrescription(
            @RequestBody Prescription prescription, 
            @PathVariable String token) {
        
        // Only a doctor should be able to write a prescription
        Map<String, String> errors = service.validateToken(token, "doctor");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
        }

        // Business Logic: Mark appointment as Completed (status 1)
        appointmentService.changeStatus(1, prescription.getAppointmentId());
        
        return prescriptionService.savePrescription(prescription);
    }

    /**
     * Retrieves prescription details for a specific appointment ID.
     */
    @GetMapping("/{appointmentId}/{token}")
    public ResponseEntity<Map<String, Object>> getPrescription(
            @PathVariable Long appointmentId, 
            @PathVariable String token) {
        
        // Both doctors and patients need to see prescriptions
        if (service.validateToken(token, "doctor").isEmpty() || 
            service.validateToken(token, "patient").isEmpty()) {
            return prescriptionService.getPrescription(appointmentId);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}