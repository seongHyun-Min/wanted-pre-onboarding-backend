package com.example.wantedboard.util;

import com.example.wantedboard.config.security.Token;
import com.example.wantedboard.exception.user.UnauthorizedUserException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    @Value("${JWT_ACCESS_EXPIRE}")
    private Long accessTokenExpiration;

    public Token createAccessToken(String payLoad) {
        String token = createToken(payLoad, accessTokenExpiration);
        return getToken(token, accessTokenExpiration);
    }

    private String createToken(String payLoad, Long tokenExpiration) {
        Claims claims = Jwts.claims().setSubject(payLoad);
        Date accessTokenExpiresIn = new Date(new Date().getTime() + tokenExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private Token getToken(String token, Long expiration) {
        return Token.builder()
                .tokenValue(token)
                .expiredTime(expiration)
                .build();
    }

    public String getPayLoad(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new UnauthorizedUserException("로그인이 필요합니다.");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 에 오류가 존재합니다.");
        }
        return false;
    }
}
