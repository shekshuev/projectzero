package ru.afso.projectzero.models;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public class RoleModel implements GrantedAuthority {

    private final String name;

    public RoleModel(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleModel roleModel = (RoleModel) o;
        return Objects.equals(name, roleModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
