package com.project.back_end.DTO;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Appointment.
 * Optimized for frontend display.
 */
public class AppointmentDTO {
    private Long id;
    private String doctorName;
    private String patientName;
    private LocalDateTime appointmentTime;
    private int status;

    public AppointmentDTO(Long id, String doctorName, String patientName, LocalDateTime appointmentTime, int status) {
        this.id = id;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}