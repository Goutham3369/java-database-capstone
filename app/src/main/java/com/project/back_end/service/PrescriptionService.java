package com.project.back_end.service;

import com.project.back_end.model.Prescription;
import com.project.back_end.repo.PrescriptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for Prescription operations.
 * Communicates with MongoDB to store and retrieve medical notes.
 */
@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * Saves a new prescription linked to an appointment.
     */
    public ResponseEntity<Map<String, String>> savePrescription(Prescription prescription) {
        Map<String, String> response = new HashMap<>();
        try {
            prescriptionRepository.save(prescription);
            response.put("message", "Prescription saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("message", "Error saving prescription: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Retrieves prescription details for a specific appointment.
     */
    public ResponseEntity<Map<String, Object>> getPrescription(Long appointmentId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Prescription> prescription = prescriptionRepository.findByAppointmentId(appointmentId);

        if (prescription.isPresent()) {
            response.put("prescription", prescription.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "No prescription found for this appointment");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}