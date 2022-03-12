package com.xiaocai.mywiki.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${test.hello}")
    private String vaule;

    @GetMapping("hello")
    public String  testHello(){
        return vaule;
    }
}
