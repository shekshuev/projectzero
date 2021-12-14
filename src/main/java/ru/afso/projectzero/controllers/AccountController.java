package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.dto.AccountDTO;
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
    public ApiResponse<AccountModel> getAccount(@PathVariable long id) {
        AccountEntity account = accountService.getAccountById(id);
        if (account != null) {
            return new SuccessResponse<>(account.toModel());
        } else {
            return new ErrorResponse<>(null);
        }
    }

    @PostMapping(consumes = { "application/json" })
    public ApiResponse<?> createAccount(@RequestBody AccountDTO accountDTO) {
        try {
            return new SuccessResponse<>(accountService.createAccount(new AccountEntity(accountDTO)).toModel());
        } catch (DataAccessException e) {
            return new ErrorResponse<>(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ApiResponse<?> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable long id) {
        AccountEntity account = new AccountEntity(accountDTO);
        account.setId(id);
        try {
            return new SuccessResponse<>(accountService.updateAccount(account).toModel());
        } catch (DataAccessException e) {
            return new ErrorResponse<>(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteAccount(@PathVariable long id) {
        try {
            accountService.deleteAccountById(id);
            return new SuccessResponse<>(true);
        } catch (DataAccessException e) {
            return new ErrorResponse<>(e.getMessage());
        }
    }

}
