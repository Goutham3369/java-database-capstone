package com.project.back_end.mvc;

import com.project.back_end.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling Dashboard UI routing.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final AuthService authService;

    public DashboardController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String showDashboard() {
        return "dashboard"; // Assuming dashboard.html or dashboard.jsp exists
    }
}