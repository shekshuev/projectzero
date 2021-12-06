package ru.afso.projectzero.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.afso.projectzero.entities.AccountEntity;

import java.util.Date;

public class NewAccountModel {

    private String userName;

    private String firstName;

    private String middleName;

    private String lastName;

    private String password;

    private String role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AccountEntity toEntity() {
        AccountEntity account = new AccountEntity();
        account.setUserName(userName);
        account.setFirstName(firstName);
        account.setMiddleName(middleName);
        account.setLastName(lastName);
        account.setRole(role);
        account.setPasswordHash(new BCryptPasswordEncoder().encode(password));
        account.setCreatedAt(new Date());
        return account;
    }
}
