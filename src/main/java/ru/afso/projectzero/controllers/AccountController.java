package ru.afso.projectzero.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.dto.AccountDTO;
import ru.afso.projectzero.entities.AccountEntity;
import ru.afso.projectzero.models.AccountModel;
import ru.afso.projectzero.models.ResponseAccountListModel;
import ru.afso.projectzero.services.AccountService;
import ru.afso.projectzero.utils.BaseResponse;
import ru.afso.projectzero.utils.SuccessResponse;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/account")
@ApiOperation(value = "Get accounts", notes = "Get all accounts with pagination")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Get accounts", notes = "Get all accounts with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "count",
                    value = "Count of accounts to return",
                    defaultValue = "5",
                    dataType = "Integer",
                    paramType = "query"),
            @ApiImplicitParam(name = "offset",
                    value = "Accounts offset, i.e. from which account return",
                    defaultValue = "5",
                    dataType = "Integer",
                    paramType = "query")
    })
    @ApiResponse(code = 200, message = "Returns total account count and account list",
            response = ResponseAccountListModel.class)
    @GetMapping
    public BaseResponse<ResponseAccountListModel> getAccounts(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        return new SuccessResponse<>(new ResponseAccountListModel(
                accountService.getTotalAccountsCount(),
                accountService.getAccounts(offset, count)
                        .stream().map(AccountEntity::toModel).collect(Collectors.toList())));
    }

    @ApiOperation(value = "Get account", notes = "Get account by ID")
    @ApiImplicitParam(name = "id",
            value = "Account ID",
            required = true,
            dataType = "Integer",
            paramType = "path")
    @ApiResponse(code = 200, message = "Returns account by its ID", response = AccountModel.class)
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse<AccountModel> getAccount(@PathVariable long id) {
        return new SuccessResponse<>(accountService.getAccountById(id).toModel());
    }

    @PostMapping(consumes = { "application/json" })
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse<?> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return new SuccessResponse<>(accountService.createAccount(new AccountEntity(accountDTO)).toModel());
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse<?> updateAccount(@Valid @RequestBody AccountDTO accountDTO, @PathVariable long id) {
        AccountEntity account = new AccountEntity(accountDTO);
        account.setId(id);
        return new SuccessResponse<>(accountService.updateAccount(account).toModel());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse<?> deleteAccount(@PathVariable long id) {
        accountService.deleteAccountById(id);
        return new SuccessResponse<>(true);
    }

}
