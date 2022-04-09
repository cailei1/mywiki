package com.xiaocai.mywiki.controller;


import com.xiaocai.mywiki.domain.Test;
import com.xiaocai.mywiki.service.TestService;
import com.xiaocai.mywiki.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Value("${test.hello}")
    private String vaule;

    @Autowired
    TestServiceImpl testService;

    @GetMapping("hello")
    public List<Test> testHello() {
        return testService.queryAll();
    }
}
