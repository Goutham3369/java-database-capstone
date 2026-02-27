package com.project.back_end.DTO;

/**
 * The Login DTO is used to capture the username/email and password
 * from the login screen so the server can check them.
 */
public class Login {
    private String username;
    private String password;

    // Constructors
    public Login() {}

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}