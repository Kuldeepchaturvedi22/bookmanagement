package com.bookmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    public enum UserType {
        CONSUMER, BOOKKEEPER
    }
    
    public User() {}
    
    public User(String name, String email, UserType userType) {
        this.name = name;
        this.email = email;
        this.userType = userType;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }
}