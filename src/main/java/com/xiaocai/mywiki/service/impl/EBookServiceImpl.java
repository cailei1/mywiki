package com.xiaocai.mywiki.service.impl;

import com.xiaocai.mywiki.domain.Ebook;
import com.xiaocai.mywiki.mapper.EbookMapper;
import com.xiaocai.mywiki.service.EBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EBookServiceImpl implements EBookService {


    @Autowired
    EbookMapper ebookMapper;

    @Override
    public List<Ebook> list() {
        return ebookMapper.selectByExample(null);
    }
}
