package com.project.back_end.repo;

import com.project.back_end.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Patient entity.
 * Handles database tasks like finding patients by email for login.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Finds a patient by their email.
     * Used for the Patient login process.
     */
    Patient findByEmail(String email);

    /**
     * Checks if a patient already exists with this email.
     * Useful for the registration step to avoid duplicates.
     */
    boolean existsByEmail(String email);
}