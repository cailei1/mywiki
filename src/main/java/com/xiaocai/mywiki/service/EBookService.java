package com.xiaocai.mywiki.service;

import com.xiaocai.mywiki.domain.Ebook;
import com.xiaocai.mywiki.request.EbookRequest;
import com.xiaocai.mywiki.request.EbookSaveRequest;
import com.xiaocai.mywiki.response.EBookResponse;
import com.xiaocai.mywiki.response.PageResponse;

import java.util.List;

public interface EBookService {
    List<Ebook> list();

    PageResponse<EBookResponse> searchBooksByName(EbookRequest ebookRequest);

    Boolean saveBook(EbookSaveRequest ebookSaveRequest);

    Boolean deleteBook(Long id);
}
