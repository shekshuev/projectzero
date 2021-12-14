package ru.afso.projectzero.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.afso.projectzero.dto.AccountDTO;
import ru.afso.projectzero.models.AccountModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "accounts")
public class AccountEntity implements ModelConvertable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String passwordHash;

    private String firstName;

    private String middleName;

    private String lastName;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private String role;



    public AccountEntity() {}

    public AccountEntity(AccountDTO accountDTO) {
        userName = accountDTO.getUserName();
        firstName = accountDTO.getFirstName();
        middleName = accountDTO.getMiddleName();
        lastName = accountDTO.getLastName();
        role = accountDTO.getRole();
        passwordHash = new BCryptPasswordEncoder().encode(accountDTO.getPassword());
        createdAt = new Date();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
        model.setId(this.id);
        model.setUserName(this.userName);
        model.setFirstName(this.firstName);
        model.setLastName(this.lastName);
        model.setMiddleName(this.middleName);
        model.setRole(this.role);
        return model;
    }
}
