package com.example.mnraderbackend.common.jwt;

import com.example.mnraderbackend.common.exception.jwt.JwtInvalidTokenException;
import com.example.mnraderbackend.common.exception.jwt.JwtMalformedTokenException;
import com.example.mnraderbackend.common.exception.jwt.JwtUnauthorizedTokenException;
import com.example.mnraderbackend.common.exception.jwt.JwtUnsupportedTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;


@Slf4j
@Component
public class JwtProvider {
    private static final String JWT_TOKEN_PREFIX = "Bearer ";


    @Value("${secret.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${secret.jwt-expired-in}")
    private long JWT_EXPIRED_IN;

    @Value("${secret.jwt-refresh-expired-in}")
    private long JWT_REFRESH_EXPIRED_IN;

    public String createToken(String principal, long userId) {
        log.info("Creating JWT token...");

        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_EXPIRED_IN);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }


    public String createRefreshToken(String principal, long userId) {
        log.info("JWT key={}", JWT_SECRET_KEY);

        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_REFRESH_EXPIRED_IN);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public String createToken_changeEmail(String principal, long userId, String accessToken) {
        log.info("JWT key={}", JWT_SECRET_KEY);

        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = getValidity(accessToken);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public boolean isExpiredToken(String token) throws JwtInvalidTokenException {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY).build()
                    .parseClaimsJws(token); // 유효성 확인
            return claims.getBody().getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            return true;
        } catch (SignatureException e) {
            throw new JwtUnauthorizedTokenException(INVALID_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new JwtUnsupportedTokenException(UNSUPPORTED_TOKEN_TYPE);
        } catch (MalformedJwtException e) {
            throw new JwtMalformedTokenException(MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new JwtInvalidTokenException(INVALID_TOKEN);
        } catch (JwtException e) {
            log.error("[JwtTokenProvider.validateAccessToken]", e);
            throw e;
        }
    }

    public String getPrincipal(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Date getValidity(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody().getExpiration();
    }


}
