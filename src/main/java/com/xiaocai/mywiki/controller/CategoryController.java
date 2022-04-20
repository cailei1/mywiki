package com.xiaocai.mywiki.controller;


import com.xiaocai.mywiki.domain.Category;
import com.xiaocai.mywiki.request.CategoryRequest;
import com.xiaocai.mywiki.request.CategorySaveRequest;
import com.xiaocai.mywiki.response.CategoryResponse;
import com.xiaocai.mywiki.response.CommonResponse;
import com.xiaocai.mywiki.response.EBookResponse;
import com.xiaocai.mywiki.response.PageResponse;
import com.xiaocai.mywiki.service.CategoryService;
import com.xiaocai.mywiki.service.EBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    CategoryService testService;

    @GetMapping("/books")
    public CommonResponse testHello() {
        List<Category> categorys = testService.list();
        return new CommonResponse().success(categorys);
    }


    @GetMapping("/bookLists")
    public CommonResponse searchBookByName(@Valid CategoryRequest categoryRequest) {

        PageResponse<CategoryResponse> eBookResponsePageResponse = testService.searchBooksByName(categoryRequest);

        return new CommonResponse().success(eBookResponsePageResponse);

    }

    @PostMapping("/save")
    public CommonResponse saveBook(@Valid @RequestBody CategorySaveRequest categoryRequest) {

        Boolean aBoolean = testService.saveCategory(categoryRequest);

        return new CommonResponse().success(aBoolean);

    }

    @DeleteMapping("/delete/{id}")
    public CommonResponse saveBook(@PathVariable Long id) {

        Boolean aBoolean = testService.deleteCategory(id);

        return new CommonResponse().success(aBoolean);

    }
}
