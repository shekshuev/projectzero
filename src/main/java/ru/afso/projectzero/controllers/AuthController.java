package ru.afso.projectzero.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.afso.projectzero.dto.JwtRequestDTO;
import ru.afso.projectzero.models.TokenResponseModel;
import ru.afso.projectzero.services.AuthService;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/api/v1.0/auth")
@Tag(name = "Auth", description = "Authentication API")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @Operation(summary = "Signin",
            description = "Signin in the API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns access and refresh tokens if authenticated"),
            @ApiResponse(responseCode = "401", description = "Wrong password",
                    content = @Content(schema = @Schema(implementation = String.class, description = "Error message")))
    })
    @PostMapping(value = "signin", consumes = { "application/json" })
    public ResponseEntity<TokenResponseModel> login(
            @Parameter(in = ParameterIn.DEFAULT, description = "Credentials to signin. Cannot be null or empty",
                    required = true, schema = @Schema(implementation = JwtRequestDTO.class))
            @RequestBody JwtRequestDTO jwtRequestDTO
    ) throws AuthException {
        return new ResponseEntity<>(authService.login(jwtRequestDTO), HttpStatus.OK);
    }
}
