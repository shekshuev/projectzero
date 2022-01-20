package ru.afso.projectzero.models;

import org.springframework.security.core.GrantedAuthority;

public class RoleModel implements GrantedAuthority {

    private final String name;

    public RoleModel(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
