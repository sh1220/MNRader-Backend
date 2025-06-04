package com.example.mnraderbackend.common.interceptor;

import com.example.mnraderbackend.common.exception.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.TOKEN_NOT_FOUND;
import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.UNSUPPORTED_TOKEN_TYPE;


@Slf4j
@Component
@RequiredArgsConstructor
public class GetJwtInterceptor implements HandlerInterceptor {

    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    // 컨트롤러 호출전에 JWT 검증
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 토큰 값 가져오기
        String accessToken = resolveAccessToken(request);

        request.setAttribute("accessToken", accessToken);
        return true;

    }

    private String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateToken(token);
        return token.substring(JWT_TOKEN_PREFIX.length());
    }

    private void validateToken(String token) {
        if (token == null) {
            throw new JwtException(TOKEN_NOT_FOUND);
        }
        if (!token.startsWith(JWT_TOKEN_PREFIX)) {
            throw new JwtException(UNSUPPORTED_TOKEN_TYPE);
        }
    }

}