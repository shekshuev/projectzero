package ru.afso.projectzero.dto;

import javax.validation.constraints.NotBlank;

public class JwtRequestDTO {

    @NotBlank(message = "Login is required!")
    private String login;

    @NotBlank(message = "Password is required!")
    private String password;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
