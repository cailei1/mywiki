package com.xiaocai.mywiki.service;

import com.xiaocai.mywiki.domain.Ebook;
import com.xiaocai.mywiki.request.EbookRequest;
import com.xiaocai.mywiki.response.EBookResponse;

import java.util.List;

public interface EBookService {
    List<Ebook> list();

    List<EBookResponse> searchBooksByName(EbookRequest ebookRequest);
}
