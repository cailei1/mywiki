package com.xiaocai.mywiki.controller;


import com.xiaocai.mywiki.domain.Ebook;
import com.xiaocai.mywiki.domain.Test;
import com.xiaocai.mywiki.response.CommonResponse;
import com.xiaocai.mywiki.service.EBookService;
import com.xiaocai.mywiki.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EBookController {


    @Autowired
    EBookService testService;

    @GetMapping("/books")
    public CommonResponse testHello() {
        List<Ebook> ebooks = testService.list();
        return new CommonResponse().success(ebooks);
    }
}
