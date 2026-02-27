package com.project.back_end.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a medical appointment, linking doctors and patients.
 * Mapped as a JPA entity for relational database storage.
 */
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Doctor is required")
    @ManyToOne
    private Doctor doctor;

    @NotNull(message = "Patient is required")
    @ManyToOne
    private Patient patient;

    @NotNull(message = "Appointment time is required")
    @Future(message = "Appointment time must be in the future")
    private LocalDateTime appointmentTime;

    @NotNull(message = "Status is required")
    private int status; // 0 for Scheduled, 1 for Completed

    public Appointment() {}

    // --- Helper Methods (Transient) ---

    /**
     * Calculates the end time (1 hour after start).
     */
    @Transient
    public LocalDateTime getEndTime() {
        return appointmentTime != null ? appointmentTime.plusHours(1) : null;
    }

    /**
     * Extracts the date portion for UI display.
     */
    @Transient
    public LocalDate getAppointmentDate() {
        return appointmentTime != null ? appointmentTime.toLocalDate() : null;
    }

    /**
     * Extracts the time portion for UI display.
     */
    @Transient
    public LocalTime getAppointmentTimeOnly() {
        return appointmentTime != null ? appointmentTime.toLocalTime() : null;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}