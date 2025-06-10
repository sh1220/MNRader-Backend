package com.example.mnraderbackend.common.interceptor;

import com.example.mnraderbackend.common.exception.AuthException;
import com.example.mnraderbackend.common.exception.jwt.JwtExpiredTokenException;
import com.example.mnraderbackend.common.exception.jwt.JwtInvalidTokenException;
import com.example.mnraderbackend.common.exception.jwt.JwtNoTokenException;
import com.example.mnraderbackend.common.exception.jwt.JwtUnsupportedTokenException;
import com.example.mnraderbackend.common.jwt.JwtProvider;
import com.example.mnraderbackend.domain.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private static final String JWT_TOKEN_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    // 컨트롤러 호출전에 JWT 검증
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 토큰 값 가져오기
        String accessToken = resolveAccessToken(request);
        // 만료 확인
        validateAccessToken(accessToken);

        String email = jwtProvider.getPrincipal(accessToken);
        validatePayload(email);

        long userId = authService.getUserIdByEmailAndValidate(email);
        request.setAttribute("userId", userId);
        return true;

    }

    private String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateToken(token);
        return token.substring(JWT_TOKEN_PREFIX.length());
    }

    private void validateToken(String token) {
        if (token == null) {
            throw new AuthException(ACCESS_DENIED, "로그인이 필요합니다.");
        }
        if (!token.startsWith(JWT_TOKEN_PREFIX)) {
            throw new JwtUnsupportedTokenException(UNSUPPORTED_TOKEN_TYPE);
        }
    }

    private void validateAccessToken(String accessToken) {
        if (jwtProvider.isExpiredToken(accessToken)) {
            throw new JwtExpiredTokenException(EXPIRED_TOKEN);
        }
    }

    private void validatePayload(String email) {
        if (email == null) {
            throw new JwtInvalidTokenException(INVALID_TOKEN);
        }
    }

}