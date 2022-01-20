package ru.afso.projectzero.models;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

public class JwtAuthentification implements Authentication {

    private boolean authenticated;
    private final String username;
    private final String firstName;
    private final Set<RoleModel> roles;

    public JwtAuthentification(boolean authenticated, String username, String firstName, Set<RoleModel> roles) {
        this.authenticated = authenticated;
        this.username = username;
        this.firstName = firstName;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return firstName;
    }
}
