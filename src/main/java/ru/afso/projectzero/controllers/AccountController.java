package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.entities.AccountEntity;
import ru.afso.projectzero.models.AccountModel;
import ru.afso.projectzero.services.AccountService;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.ErrorResponse;
import ru.afso.projectzero.utils.SuccessResponse;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ApiResponse<HashMap<String, Object>> getAccounts(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", accountService.getTotalAccountsCount());
        map.put("accounts", accountService.getAccounts(offset, count).stream().map(AccountEntity::toModel).collect(Collectors.toList()));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountModel> getAccount(@PathVariable String id) {
        AccountEntity account = accountService.getAccountById(id);
        if (account != null) {
            return new SuccessResponse<>(account.toModel());
        } else {
            return new ErrorResponse<>(null);
        }
    }

    @PostMapping(consumes = { "application/json" })
    public ApiResponse<AccountModel> createAccount(@RequestBody AccountEntity account) {
        return new SuccessResponse<>(accountService.createAccount(account).toModel());
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ApiResponse<AccountModel> updateAccount(@RequestBody AccountEntity account, @PathVariable String id) {
        account.setId(id);
        return new SuccessResponse<>(accountService.updateAccount(account).toModel());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteAccount(@PathVariable String id) {
        accountService.deleteAccountById(id);
        return new SuccessResponse<>(true);
    }

}
