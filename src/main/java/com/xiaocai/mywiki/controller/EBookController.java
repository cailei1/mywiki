package com.xiaocai.mywiki.controller;


import com.xiaocai.mywiki.domain.Ebook;
import com.xiaocai.mywiki.domain.Test;
import com.xiaocai.mywiki.request.EbookRequest;
import com.xiaocai.mywiki.request.EbookSaveRequest;
import com.xiaocai.mywiki.response.CommonResponse;
import com.xiaocai.mywiki.response.EBookResponse;
import com.xiaocai.mywiki.response.PageResponse;
import com.xiaocai.mywiki.service.EBookService;
import com.xiaocai.mywiki.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EBookController {


    @Autowired
    EBookService testService;

    @GetMapping("/books")
    public CommonResponse testHello() {
        List<Ebook> ebooks = testService.list();
        return new CommonResponse().success(ebooks);
    }


    @GetMapping("/bookLists")
    public CommonResponse searchBookByName(@Valid EbookRequest ebookRequest) {

        PageResponse<EBookResponse> eBookResponsePageResponse = testService.searchBooksByName(ebookRequest);

        return new CommonResponse().success(eBookResponsePageResponse);

    }

    @PostMapping("/save")
    public CommonResponse saveBook(@Valid @RequestBody EbookSaveRequest ebookRequest) {

        Boolean aBoolean = testService.saveBook(ebookRequest);

        return new CommonResponse().success(aBoolean);

    }

    @DeleteMapping("/delete/{id}")
    public CommonResponse saveBook(@PathVariable Long id) {

        Boolean aBoolean = testService.deleteBook(id);

        return new CommonResponse().success(aBoolean);

    }
}
