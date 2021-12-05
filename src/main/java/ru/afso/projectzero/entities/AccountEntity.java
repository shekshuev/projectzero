package ru.afso.projectzero.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import ru.afso.projectzero.models.AccountModel;

import java.util.Date;

@Document(collection = "accounts")
public class AccountEntity extends BaseEntity implements ModelConvertable{

    private String username;

    private String passwordHash;

    private String firstName;

    private String lastName;

    private String surname;

    private Date createdAt;

    private String role;

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public AccountModel toModel() {
        AccountModel model = new AccountModel();
        model.setId(this.getId());
        model.setUsername(this.username);
        model.setFirstName(this.firstName);
        model.setLastName(this.lastName);
        model.setSurname(this.surname);
        model.setCreatedAt(this.createdAt);
        model.setRole(this.role);
        return model;
    }
}
