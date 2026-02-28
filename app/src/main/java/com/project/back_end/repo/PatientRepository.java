package com.project.back_end.repo;

import com.project.back_end.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Patient entity.
 * Handles database tasks like finding patients by email or phone.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Finds a patient by their email.
     * Used for the Patient login process.
     */
    Optional<Patient> findByEmail(String email);

    /**
     * Checks if a patient already exists with this email.
     * Useful for the registration step to avoid duplicates.
     */
    boolean existsByEmail(String email);

   
    @Query("SELECT p FROM Patient p WHERE p.email = :identifier OR p.phone = :identifier")
    Optional<Patient> findByEmailOrPhone(@Param("identifier") String identifier);
}
