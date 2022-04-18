package com.xiaocai.mywiki.response;

import java.util.List;

public class PageResponse<T> {

    private int total;

    private List<T> pageLists;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getPageLists() {
        return pageLists;
    }

    public void setPageLists(List<T> pageLists) {
        this.pageLists = pageLists;
    }
}
