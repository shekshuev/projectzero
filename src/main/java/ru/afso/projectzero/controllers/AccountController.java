package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.models.Account;
import ru.afso.projectzero.repositories.AccountRepository;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.SuccessResponse;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/account")
public class AccountController {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public ApiResponse<HashMap<String, Object>> getAccounts(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", accountRepository.count());
        map.put("accounts", accountRepository.findAll()
                .stream().skip(offset).limit(count).collect(Collectors.toList()));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/{id}")
    public ApiResponse<Account> getAccount(@PathVariable String id) {
        return new SuccessResponse<>(accountRepository.findById(id).orElse(null));
    }

    @PostMapping(consumes = { "application/json" })
    public ApiResponse<Account> createAccount(@RequestBody Account account) {
        accountRepository.save(account);
        return new SuccessResponse<>(account);
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ApiResponse<Account> updateAccount(@RequestBody Account account, @PathVariable String id) {
        account.setId(id);
        accountRepository.save(account);
        return new SuccessResponse<>(account);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteAccount(@PathVariable String id) {
        accountRepository.deleteById(id);
        return new SuccessResponse<>(true);
    }

}
