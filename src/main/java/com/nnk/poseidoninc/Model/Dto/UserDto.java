package com.nnk.poseidoninc.Model.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {
    private int userId;
    @NotBlank
    @Size(max = 125)
    @Email
    private String email;
    @NotBlank
    //TODO : password validation
    private String password;
    @NotBlank
    @Size(max = 125)
    private String fullName;
    @NotBlank
    @Size(max = 125)
    private String role;

    public UserDto(String email, String password, String fullName, String role) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public UserDto() {
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
