package com.jinjin.springboot.config.auth;

import com.jinjin.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    /*
    - 특정 파라미터를 지원하는 지 확인

    -LoginUser어노테이션이 파라미터에 붙어 있는가?
    -세션에 SessionUser클래스가 존재하는가?
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isLoginUserAnnotation;
    }

    /*
    - 파라미터에 전달할 객체를 생성

    suppertsParameter가 ture 반환하면 실행되어 파라미터에 SessionUser 대입
     */
    @Override
    public SessionUser resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return (SessionUser) httpSession.getAttribute("user");
    }


}
