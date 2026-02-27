package com.project.back_end.repo;

import com.project.back_end.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Doctor entity.
 * Provides methods to find doctors by email or specialty.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Finds a doctor by their email address.
     * Used for login and profile lookups.
     */
    Doctor findByEmail(String email);

    /**
     * Finds all doctors that belong to a specific specialty.
     * Useful for patients looking for specific types of care.
     */
    List<Doctor> findBySpecialty(String specialty);

    /**
     * Checks if a doctor exists in the database by their email.
     */
    boolean existsByEmail(String email);
}