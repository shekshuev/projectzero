package ru.afso.projectzero.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.dto.AccountDTO;
import ru.afso.projectzero.models.AccountModel;
import ru.afso.projectzero.models.ResponseListModel;
import ru.afso.projectzero.services.AccountService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/accounts")
@Tag(name = "Accounts", description = "Accounts management API")
@SecurityScheme(name = "Administrator", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP)
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }



    @Operation(summary = "Get accounts",
            description = "Returns all accounts with pagination",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns total account count and account list"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping(produces = {"application/json"})
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseListModel<AccountModel>> getAccounts(
            @Parameter(name = "count", in = ParameterIn.QUERY, description = "Accounts count to return, default is 5")
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @Parameter(name = "offset", in = ParameterIn.QUERY, description = "From which account return, default is 0")
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        return new ResponseEntity<>(new ResponseListModel<>(
                accountService.getTotalAccountsCount(),
                accountService.getAccounts(offset, count)), HttpStatus.OK);
    }



    @Operation(summary = "Get account by ID",
            description = "Return account by its ID",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return account by its ID"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping(value="/{id}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<AccountModel> getAccount(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Account id")
            @PathVariable long id
    ) {
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }



    @Operation(summary = "Create account",
            description = "Creates new account",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns empty body if account was created", headers = {
                    @Header(name = "Location", description = "Contains account location URI")
            }),
            @ApiResponse(responseCode = "400", description = "Other error",
                    content = @Content(schema = @Schema(implementation = String.class, description = "Error message"))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping(consumes = { "application/json" })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> createAccount(
            @Parameter(in = ParameterIn.DEFAULT, description = "Account to add. Cannot be null or empty",
                    required = true, schema = @Schema(implementation = AccountDTO.class))
            @Valid @RequestBody AccountDTO accountDTO
    ) {
        AccountModel model = accountService.createAccount(accountDTO);
        URI location = URI.create(String.format("/accounts/%d", model.getId()));
        return ResponseEntity.created(location).build();
    }



    @Operation(summary = "Update account",
            description = "Updates existing account",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Returns empty body if account was updated"),
            @ApiResponse(responseCode = "400", description = "Other error",
                    content = @Content(schema = @Schema(implementation = String.class, description = "Error message"))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PutMapping(value = "/{id}", consumes = { "application/json" }, produces = {"application/json"})
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> updateAccount(
            @Parameter(in = ParameterIn.DEFAULT, description = "Account data to update",
                    required = true, schema = @Schema(implementation = AccountDTO.class))
            @Valid @RequestBody AccountDTO accountDTO,
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Account id")
            @PathVariable long id
    ) {
        accountService.updateAccount(id, accountDTO);
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "Delete account",
            description = "Deletes existing account",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Returns empty body if account was deleted"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Account id")
            @PathVariable long id
    ) {
        accountService.deleteAccountById(id);
        return ResponseEntity.noContent().build();
    }

}
