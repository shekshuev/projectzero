package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.afso.projectzero.dto.JwtRequestDTO;
import ru.afso.projectzero.models.TokenResponseModel;
import ru.afso.projectzero.services.AuthService;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.SuccessResponse;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/api/v1.0/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signin")
    public ApiResponse<TokenResponseModel> login(@RequestBody JwtRequestDTO jwtRequestDTO) throws AuthException {
        return new SuccessResponse<>(authService.login(jwtRequestDTO));
    }
}
