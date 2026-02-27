package com.project.back_end.service;

import com.project.back_end.model.Appointment;
import com.project.back_end.model.Doctor;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class handling logic for Doctor-related operations.
 */
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Retrieves all appointments for a specific doctor within a time range.
     */
    public List<Appointment> getAppointments(Long doctorId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, start, end);
    }

    /**
     * Fetches doctor profile by email.
     */
    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    /**
     * Adds or updates a doctor in the system.
     */
    public Map<String, Object> saveDoctor(Doctor doctor) {
        Map<String, Object> response = new HashMap<>();
        try {
            Doctor savedDoctor = doctorRepository.save(doctor);
            response.put("doctor", savedDoctor);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    /**
     * Deletes a doctor and cleans up their appointments.
     */
    @Transactional
    public void deleteDoctor(long id) {
        appointmentRepository.deleteAllByDoctorId(id);
        doctorRepository.deleteById(id);
    }

    /**
     * Fetches all doctors by their specialty.
     */
    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }
}