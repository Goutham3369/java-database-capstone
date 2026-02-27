package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a scheduled appointment between a patient and a doctor.
 * Mapped to a database table via JPA.
 */
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Doctor doctor;

    @NotNull
    @ManyToOne
    private Patient patient;

    @Future
    private LocalDateTime appointmentTime;

    @NotNull
    private Integer status; // 0 = scheduled, 1 = completed

    /**
     * Default no-argument constructor required by JPA.
     */
    public Appointment() {
    }

    /**
     * Parameterized constructor for easy object creation.
     */
    public Appointment(Doctor doctor, Patient patient, LocalDateTime appointmentTime, Integer status) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // --- Calculated Utility Methods ---

    /**
     * Calculates the estimated end time of the appointment.
     * @return LocalDateTime representing 1 hour after the start time.
     */
    @Transient
    public LocalDateTime getEndTime() {
        if (appointmentTime == null) return null;
        return appointmentTime.plusHours(1);
    }

    /**
     * Extracts only the date part of the scheduled appointment.
     * @return LocalDate of the appointment.
     */
    @Transient
    public LocalDate getAppointmentDate() {
        if (appointmentTime == null) return null;
        return appointmentTime.toLocalDate();
    }

    /**
     * Extracts only the time part of the scheduled appointment.
     * @return LocalTime of the appointment.
     */
    @Transient
    public LocalTime getAppointmentTimeOnly() {
        if (appointmentTime == null) return null;
        return appointmentTime.toLocalTime();
    }

    // --- Standard Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}