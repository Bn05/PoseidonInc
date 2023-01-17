package com.nnk.poseidoninc.Model.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class UserDto {
    private int userId;
    @NotBlank
    @Size(max = 125)
    private String userName;
    @NotBlank
    //TODO : password validation
    private String password;
    @NotBlank
    @Size(max = 125)
    private String fullName;
    @NotBlank
    @Size(max = 125)
    private String role;

    public UserDto(String userName, String password, String fullName, String role) {
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", email='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (userId != userDto.userId) return false;
        if (!Objects.equals(userName, userDto.userName)) return false;
        if (!Objects.equals(password, userDto.password)) return false;
        if (!Objects.equals(fullName, userDto.fullName)) return false;
        return Objects.equals(role, userDto.role);
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
