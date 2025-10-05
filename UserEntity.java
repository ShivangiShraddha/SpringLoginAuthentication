package com.sts_LoginAuthentication.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // For both local and OAuth2 login
    @Column(nullable = false, unique = true)
    private String username; // email for Google login, or chosen username for normal login

    private String password; // blank for OAuth2 users

    private String role; // e.g., ROLE_USER, ROLE_ADMIN

    // Extra details
    private String firstName;
    private String lastName;

    private String mobileNumber;

    @Column(length = 500)
    private String photoUrl; // profile picture from Google or uploaded by user

    private LocalDate dateOfBirth;

    public UserEntity() {}

    public UserEntity(String username, String password, String role, String firstName, String lastName,
                      String mobileNumber, String photoUrl, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.photoUrl = photoUrl;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

	
}
