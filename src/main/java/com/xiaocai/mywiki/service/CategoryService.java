package com.xiaocai.mywiki.service;

import com.xiaocai.mywiki.domain.Category;
import com.xiaocai.mywiki.request.CategoryRequest;
import com.xiaocai.mywiki.request.CategorySaveRequest;
import com.xiaocai.mywiki.response.CategoryResponse;
import com.xiaocai.mywiki.response.PageResponse;

import java.util.List;

public interface CategoryService {
    List<Category> list();

    PageResponse<CategoryResponse> searchBooksByName(CategoryRequest categoryRequest);

    Boolean saveCategory(CategorySaveRequest categorySaveRequest);

    Boolean deleteCategory(Long id);
}
