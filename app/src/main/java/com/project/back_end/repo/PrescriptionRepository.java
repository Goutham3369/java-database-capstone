package com.project.back_end.repo;

import com.project.back_end.model.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Prescription entity.
 * Uses MongoRepository because prescriptions are stored in MongoDB.
 */
@Repository
public interface PrescriptionRepository extends MongoRepository<Prescription, String> {

    /**
     * Finds a prescription based on the appointment ID.
     * This links the MySQL appointment to the MongoDB medical notes.
     */
    Optional<Prescription> findByAppointmentId(Long appointmentId);
}