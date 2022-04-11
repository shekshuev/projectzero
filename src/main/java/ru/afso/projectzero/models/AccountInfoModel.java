package ru.afso.projectzero.models;

import java.util.Set;

public class AccountInfoModel {

    private String username;

    private String firstname;

    private Set<RoleModel> roles;


    public AccountInfoModel(String username, String firstname, Set<RoleModel> roles) {
        this.username = username;
        this.firstname = firstname;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }
}
