package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.models.Account;
import ru.afso.projectzero.repositories.AccountRepository;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/api/v1.0/account")
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/api/v1.0/account/{id}")
    public String getAccount(@PathVariable String id) {
        return id;
    }

    @PostMapping(value = "/api/v1.0/account", consumes = { "application/json" })
    public Account createAccount(@RequestBody Account account) {
        accountRepository.save(account);
        return account;
    }

    @PutMapping(value="/api/v1.0/account/{id}", consumes = { "application/json" })
    public Account updateAccount(@RequestBody final Account account, @PathVariable String id) {
        account.setId(id);
        accountRepository.save(account);
        return account;
    }

    @DeleteMapping("/api/v1.0/account/{id}")
    public String deleteAccount(@PathVariable String id) {
        accountRepository.deleteById(id);
        return "ok";
    }

}
