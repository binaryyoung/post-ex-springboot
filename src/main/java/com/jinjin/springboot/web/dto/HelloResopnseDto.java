package com.jinjin.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResopnseDto {
    private final String name;
    private final int amount;
}
