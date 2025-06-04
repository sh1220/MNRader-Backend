package com.example.mnraderbackend.common.config;

import com.example.mnraderbackend.common.argument_resolver.JwtAuthHandlerArgumentResolver;
import com.example.mnraderbackend.common.interceptor.GetJwtInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.mnraderbackend.common.argument_resolver.GetJwtHandlerArgumentResolver;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    private final GetJwtInterceptor getJwtInterceptor;
    private final JwtAuthHandlerArgumentResolver jwtAuthHandlerArgumentResolver;
    private final GetJwtHandlerArgumentResolver getJwtHandlerArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                 //인터셉터 적용 범위 수정
        registry.addInterceptor(getJwtInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/login", "/auth/signup");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jwtAuthHandlerArgumentResolver);
        resolvers.add(getJwtHandlerArgumentResolver);
    }
}