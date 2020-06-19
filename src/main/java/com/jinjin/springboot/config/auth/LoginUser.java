package com.jinjin.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 어노테이션이_생성될_수_있는_위치_지정
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
