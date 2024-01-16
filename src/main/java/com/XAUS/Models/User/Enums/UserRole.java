package com.XAUS.Models.User.Enums;

public enum UserRole {
    ADMIN("admin"),
    SALES("sales"),
    PACKAGER("packager");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
