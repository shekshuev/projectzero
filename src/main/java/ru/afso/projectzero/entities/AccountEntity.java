package ru.afso.projectzero.entities;

import ru.afso.projectzero.models.AccountModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "accounts")
public class AccountEntity implements ModelConvertable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    private String firstName;

    private String lastName;

    private String surname;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
