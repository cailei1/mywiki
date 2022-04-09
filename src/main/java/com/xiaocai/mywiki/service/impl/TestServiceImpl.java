package com.xiaocai.mywiki.service.impl;

import com.xiaocai.mywiki.domain.Test;
import com.xiaocai.mywiki.mapper.TestMapper;
import com.xiaocai.mywiki.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestMapper testMapper;


    @Override
    public List<Test> queryAll() {

        return testMapper.list();
    }
}
