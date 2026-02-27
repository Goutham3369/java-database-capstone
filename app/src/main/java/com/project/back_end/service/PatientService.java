package com.project.back_end.service;

import com.project.back_end.DTO.AppointmentDTO;
import com.project.back_end.model.Appointment;
import com.project.back_end.model.Patient;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Patient operations.
 * Handles profiles and appointment history using DTOs.
 */
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public PatientService(PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Maps an Appointment entity to an AppointmentDTO.
     */
    private AppointmentDTO convertToDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getDoctor().getName(),
                appointment.getPatient().getName(),
                appointment.getAppointmentTime(),
                appointment.getStatus()
        );
    }

    /**
     * Gets all appointments for a patient.
     */
    public List<AppointmentDTO> getAllAppointments(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filters appointments by status (e.g., Upcoming or Past).
     */
    public List<AppointmentDTO> getAppointmentsByStatus(Long patientId, int status) {
        return appointmentRepository.findByPatient_IdAndStatusOrderByAppointmentTimeAsc(patientId, status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filters appointments by doctor name.
     */
    public List<AppointmentDTO> filterByDoctor(String doctorName, Long patientId) {
        return appointmentRepository.filterByDoctorNameAndPatientId(doctorName, patientId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Fetches a patient's profile by email.
     */
    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    /**
     * Saves or updates a patient profile.
     */
    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }
}