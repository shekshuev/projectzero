package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.models.Account;
import ru.afso.projectzero.repositories.AccountRepository;

import java.util.HashMap;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/api/v1.0/account")
    public HashMap<String, Object> getAccounts() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("payload", accountRepository.findAll());
        return map;
    }

    @GetMapping("/api/v1.0/account/{id}")
    public String getAccount(@PathVariable String id) {
        return id;
    }

    @PostMapping("/api/v1.0/account")
    public HashMap<String, Object> createAccount(@RequestBody Account account) {
        accountRepository.save(account);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("payload", account);
        return map;
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
