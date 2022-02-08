package ru.afso.projectzero.services;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.dto.JwtRequestDTO;
import ru.afso.projectzero.entities.AccountEntity;
import ru.afso.projectzero.models.TokenResponseModel;

import javax.security.auth.message.AuthException;

@Service
public class AuthService {

    private final AccountService accountService;
    private final JwtService jwtService;

    @Autowired
    public AuthService(AccountService accountService, JwtService jwtService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    public TokenResponseModel login(@NonNull JwtRequestDTO jwtRequestDTO) throws AuthException {
        final AccountEntity account = accountService.getAccountByUsername(jwtRequestDTO.getLogin());
        String s1 = account.getPasswordHash();
        String s2 = new BCryptPasswordEncoder().encode(jwtRequestDTO.getPassword());
        if (new BCryptPasswordEncoder().matches(jwtRequestDTO.getPassword(), account.getPasswordHash())) {
            final String accessToken = jwtService.generateAccessToken(account.toModel());
            final String refreshToken = jwtService.generateRefreshToken(account.toModel());
            return new TokenResponseModel(accessToken, refreshToken);
        } else {
            throw new AuthException("Wrong password!");
        }
    }

    public TokenResponseModel refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final AccountEntity account = accountService.getAccountByUsername(login);
            final String accessToken = jwtService.generateAccessToken(account.toModel());
            final String newRefreshToken = jwtService.generateRefreshToken(account.toModel());
            return new TokenResponseModel(accessToken, newRefreshToken);
        }
        throw new AuthException("JWT token isn't valid!");
    }
}