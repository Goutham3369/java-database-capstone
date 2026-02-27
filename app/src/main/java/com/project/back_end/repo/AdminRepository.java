package com.project.back_end.repo;

import com.project.back_end.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Admin entity.
 * This handles the database operations for the Boss (Admin).
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Finds an Admin by their username.
     * Used during the login process to verify credentials.
     */
    Admin findByUsername(String username);
}