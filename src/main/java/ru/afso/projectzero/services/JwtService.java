package ru.afso.projectzero.services;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.models.AccountModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    private final String jwtAccessSecret;
    private final String jwtRefreshSecret;
    private final int jwtAccessTokenTtl;
    private final int jwtRefreshTokenTtl;

    @Autowired
    public JwtService(@Value("${jwt.secret.access}") String jwtAccessSecret,
                      @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
                      @Value("${jwt.secret.access.ttl}") int jwtAccessTokenTtl,
                      @Value("${jwt.secret.refresh.ttl}") int jwtRefreshTokenTtl) {
        this.jwtAccessSecret = jwtAccessSecret;
        this.jwtRefreshSecret = jwtRefreshSecret;
        this.jwtAccessTokenTtl = jwtAccessTokenTtl;
        this.jwtRefreshTokenTtl = jwtRefreshTokenTtl;
    }

    public String generateAccessToken(@NonNull AccountModel account) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(jwtAccessTokenTtl).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        final String accessToken = Jwts.builder()
                .setSubject(account.getUserName())
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .claim("role", account.getRole())
                .claim("firstName", account.getFirstName())
                .compact();
        return accessToken;
    }

    public String generateRefreshToken(@NonNull AccountModel account) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(jwtRefreshTokenTtl).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        final String refreshToken = Jwts.builder()
                .setSubject(account.getUserName())
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
        return refreshToken;
    }

    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String token) {
        return validateToken(token, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
