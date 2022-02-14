package ru.afso.projectzero.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.net.URI;
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
    public ResponseEntity<ResponseAccountListModel> getAccounts(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        return new ResponseEntity<>(new ResponseAccountListModel(
                accountService.getTotalAccountsCount(),
                accountService.getAccounts(offset, count)
                        .stream().map(AccountEntity::toModel).collect(Collectors.toList())), HttpStatus.OK);
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
    public ResponseEntity<AccountModel> getAccount(@PathVariable long id) {
        return new ResponseEntity<>(accountService.getAccountById(id).toModel(), HttpStatus.OK);
    }

    @ApiOperation(value = "Create account", notes = "Create new account")
    @ApiResponse(code = 201, message = "Returns empty body if account was created.", responseHeaders = {
            @ResponseHeader(name = "Location", description = "Contains account location URI", response = String.class)
    })
    @PostMapping(consumes = { "application/json" })
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> createAccount(@Valid @RequestBody @ApiParam(
            name="Account",
            value = "Account data in json format without ID",
            required = true,
            type = "ru.afso.projectzero.dto.AccountDTO"
    ) AccountDTO accountDTO) {
        AccountModel model = accountService.createAccount(new AccountEntity(accountDTO)).toModel();
        URI location = URI.create(String.format("/account/%d", model.getId()));
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Update account", notes = "Update existing account. All fields will be updated")
    @ApiResponse(code = 200, message = "Returns created account with its ID", response = AccountModel.class)
    @ApiImplicitParam(name = "id",
            value = "Account ID",
            required = true,
            dataType = "Integer",
            paramType = "path")
    @PutMapping(value = "/{id}", consumes = { "application/json" })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<AccountModel> updateAccount(@Valid @RequestBody @ApiParam(
            name="Account",
            value = "Account data in json format without ID",
            required = true,
            type = "ru.afso.projectzero.dto.AccountDTO"
    ) AccountDTO accountDTO, @PathVariable long id) {
        AccountEntity account = new AccountEntity(accountDTO);
        account.setId(id);
        return new ResponseEntity<>(accountService.updateAccount(account).toModel(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete account", notes = "Removes existing account.")
    @ApiResponse(code = 204, message = "Removes account by ID")
    @ApiImplicitParam(name = "id",
            value = "Account ID",
            required = true,
            dataType = "Integer",
            paramType = "path")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteAccount(@PathVariable long id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.noContent().build();
    }

}
