package com.librasys.models;

import com.google.gson.annotations.SerializedName;
import com.librasys.enums.UserRole;

public class User {
    private String password;
    private UserRole role;

    @SerializedName("ID")
    private long id;

    @SerializedName("Username")
    private String username;

    @SerializedName("Email")
    private String email;

    @SerializedName("Name")
    private String name;

    @SerializedName("Address")
    private String address;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    @SerializedName("RoleID")
    private long roleId;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email, String name, String address,
            String phoneNumber, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String nama) {
        this.name = nama;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String alamat) {
        this.address = alamat;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String noHandphone) {
        this.phoneNumber = noHandphone;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole userRole) {
        this.role = userRole;
    }

    public long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
