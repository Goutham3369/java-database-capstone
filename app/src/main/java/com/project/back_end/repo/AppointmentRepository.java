package com.project.back_end.repo;

import com.project.back_end.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Handles all database operations for Appointments.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    // Finds appointments for a specific doctor on a specific date
    @Query("SELECT a FROM Appointment a WHERE a.doctor.name = :name AND CAST(a.appointmentTime AS date) = :date")
    List<Appointment> findByDoctorNameAndDate(String name, LocalDate date);

    // Finds appointments for a doctor within a time range
    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    // Deletes all appointments for a specific doctor
    void deleteAllByDoctorId(long doctorId);

    // Finds all appointments for one patient
    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId")
    List<Appointment> findByPatientId(Long patientId);

    // Finds appointments for a patient sorted by time (for "Upcoming" or "Past" views)
    List<Appointment> findByPatient_IdAndStatusOrderByAppointmentTimeAsc(Long patientId, int status);

    // Filters appointments by doctor name for a specific patient
    @Query("SELECT a FROM Appointment a WHERE a.doctor.name = :doctorName AND a.patient.id = :patientId")
    List<Appointment> filterByDoctorNameAndPatientId(String doctorName, Long patientId);

    // Filters appointments by doctor name and status for a specific patient
    @Query("SELECT a FROM Appointment a WHERE a.doctor.name = :doctorName AND a.patient.id = :patientId AND a.status = :status")
    List<Appointment> filterByDoctorNameAndPatientIdAndStatus(String doctorName, long patientId, int status);

    // Updates only the status (like changing to 'Completed')
    @Modifying
    @Query("UPDATE Appointment a SET a.status = :status WHERE a.id = :id")
    void updateStatus(int status, Long id);
}