package com.xiaocai.mywiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaocai.mywiki.domain.Category;
import com.xiaocai.mywiki.domain.CategoryExample;
import com.xiaocai.mywiki.mapper.CategoryMapper;
import com.xiaocai.mywiki.request.CategoryRequest;
import com.xiaocai.mywiki.request.CategorySaveRequest;
import com.xiaocai.mywiki.response.CategoryResponse;
import com.xiaocai.mywiki.response.PageResponse;
import com.xiaocai.mywiki.service.CategoryService;
import com.xiaocai.mywiki.util.CopyUtil;
import com.xiaocai.mywiki.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    SnowFlake snowFlake;

    @Override
    public List<CategoryResponse> list() {
        CategoryExample categoryExample = new CategoryExample();
//        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        List<CategoryResponse> responses = CopyUtil.copyList(categories,CategoryResponse.class);
        return responses;
    }

    @Override
    public PageResponse<CategoryResponse> searchBooksByName(CategoryRequest categoryRequest) {

        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

        PageHelper.startPage(categoryRequest.getPage(), categoryRequest.getPageSize());


        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        PageInfo pageInfo = new PageInfo(categorys);
//        List<CategoryResponse> categoryResponses = new ArrayList<CategoryResponse>();
//
//        for (Category category : categorys) {
//            CategoryResponse categoryResponse = new CategoryResponse();
//            BeanUtils.copyProperties(category, categoryResponse);
//            categoryResponses.add(categoryResponse);
//
//        }
        List<CategoryResponse> categoryResponses = CopyUtil.copyList(categorys, CategoryResponse.class);
        PageResponse<CategoryResponse> categoryResponsePageResponse = new PageResponse<>();
        categoryResponsePageResponse.setPageLists(categoryResponses);
        categoryResponsePageResponse.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));

        return categoryResponsePageResponse;
    }

    @Override
    public Boolean saveCategory(CategorySaveRequest categorySaveRequest) {
        if (ObjectUtils.isEmpty(categorySaveRequest)) {
            return false;
        }
        Category category = CopyUtil.copy(categorySaveRequest, Category.class);
        if (!ObjectUtils.isEmpty(categorySaveRequest.getId())) {
            // updata category
            categoryMapper.updateByPrimaryKey(category);
            return true;
        }
        category.setId(snowFlake.nextId());
        categoryMapper.insert(category);
        return true;
    }

    @Override
    public Boolean deleteCategory(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
        return true;
    }
}
