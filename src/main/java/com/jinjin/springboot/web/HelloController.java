package com.jinjin.springboot.web;

import com.jinjin.springboot.web.dto.HelloResopnseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hi() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResopnseDto hello(@RequestParam("name") String name,
                                  @RequestParam("amount") int amount) {
        return new HelloResopnseDto(name, amount);
    }
}
