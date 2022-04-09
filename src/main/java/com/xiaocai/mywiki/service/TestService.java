package com.xiaocai.mywiki.service;

import com.xiaocai.mywiki.domain.Test;
import com.xiaocai.mywiki.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface TestService {


    List<Test> queryAll();

}
