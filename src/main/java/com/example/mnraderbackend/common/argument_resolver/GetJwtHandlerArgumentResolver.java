package com.example.mnraderbackend.common.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

@Slf4j
@Component
public class GetJwtHandlerArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(PreAccessToken.class);
        log.debug(Arrays.toString(parameter.getParameterAnnotations()));
        boolean hasType = long.class.isAssignableFrom(parameter.getParameterType())
                || Long.class.isAssignableFrom(parameter.getParameterType());
        log.info("hasAnnotation={}, hasType={}, hasAnnotation && hasType={}", hasAnnotation, hasType, hasAnnotation&&hasType);
        return hasAnnotation && hasType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        log.info("accessToken={}", request.getAttribute("accessToken"));
        return request.getAttribute("accessToken");

    }
}