package com.xiaocai.mywiki.service.impl;

import com.xiaocai.mywiki.domain.Ebook;
import com.xiaocai.mywiki.domain.EbookExample;
import com.xiaocai.mywiki.mapper.EbookMapper;
import com.xiaocai.mywiki.request.EbookRequest;
import com.xiaocai.mywiki.response.EBookResponse;
import com.xiaocai.mywiki.service.EBookService;
import com.xiaocai.mywiki.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EBookServiceImpl implements EBookService {


    @Autowired
    EbookMapper ebookMapper;

    @Override
    public List<Ebook> list() {
        return ebookMapper.selectByExample(null);
    }

    @Override
    public List<EBookResponse> searchBooksByName(EbookRequest ebookRequest) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + ebookRequest.getName() + "%");

        List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);
//        List<EBookResponse> eBookResponses = new ArrayList<EBookResponse>();
//
//        for (Ebook ebook : ebooks) {
//            EBookResponse eBookResponse = new EBookResponse();
//            BeanUtils.copyProperties(ebook, eBookResponse);
//            eBookResponses.add(eBookResponse);
//
//        }
        List<EBookResponse> eBookResponses = CopyUtil.copyList(ebooks, EBookResponse.class);
        return eBookResponses;
    }
}
