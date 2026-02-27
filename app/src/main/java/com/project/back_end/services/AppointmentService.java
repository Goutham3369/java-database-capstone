package com.project.back_end.service;

import com.project.back_end.model.Appointment;
import com.project.back_end.repo.AppointmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class to manage hospital appointments.
 * It connects the Controllers to the AppointmentRepository.
 */
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Fetches appointments based on doctor name and specific date
    public Map<String, Object> getAppointment(String name, LocalDate date, String token) {
        Map<String, Object> response = new HashMap<>();
        List<Appointment> appointments = appointmentRepository.findByDoctorNameAndDate(name, date);
        response.put("appointments", appointments);
        return response;
    }

    // Saves a new appointment to the database
    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // Updates an existing appointment's details
    @Transactional
    public ResponseEntity<Map<String, String>> updateAppointment(Appointment appointment) {
        Map<String, String> response = new HashMap<>();
        Optional<Appointment> existing = appointmentRepository.findById(appointment.getId());

        if (existing.isEmpty()) {
            response.put("message", "Appointment not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        appointmentRepository.save(appointment);
        response.put("message", "Appointment updated successfully");
        return ResponseEntity.ok(response);
    }

    // Cancels an appointment by deleting it from the database
    @Transactional
    public ResponseEntity<Map<String, String>> cancelAppointment(long id, String token) {
        Map<String, String> response = new HashMap<>();
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            response.put("message", "Appointment cancelled successfully");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Appointment not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Specifically updates the status (e.g., from 'Scheduled' to 'Completed')
    @Transactional
    public void changeStatus(int status, Long id) {
        appointmentRepository.updateStatus(status, id);
    }
}