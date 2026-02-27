package com.project.back_end.controllers;

import com.project.back_end.model.Prescription;
import com.project.back_end.service.AppointmentService;
import com.project.back_end.service.AuthService;
import com.project.back_end.service.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.path}" + "prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final AuthService authService;
    private final AppointmentService appointmentService;

    public PrescriptionController(PrescriptionService prescriptionService, 
                                  AuthService authService, 
                                  AppointmentService appointmentService) {
        this.prescriptionService = prescriptionService;
        this.authService = authService;
        this.appointmentService = appointmentService;
    }

    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> savePrescription(@RequestBody Prescription prescription, @PathVariable String token) {
        Map<String, String> errors = authService.validateToken(token, "doctor");
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
        }

        appointmentService.changeStatus(1, prescription.getAppointmentId());
        return prescriptionService.savePrescription(prescription);
    }

    @GetMapping("/{appointmentId}/{token}")
    public ResponseEntity<Map<String, Object>> getPrescription(@PathVariable Long appointmentId, @PathVariable String token) {
        if (authService.validateToken(token, "doctor").isEmpty() || authService.validateToken(token, "patient").isEmpty()) {
            return prescriptionService.getPrescription(appointmentId);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}