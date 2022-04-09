package com.xiaocai.mywiki.response;

public class CommonResponse<T> {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private int code;
    private T data;
    private String msg;


    public CommonResponse success(T data) {
        this.data = data;
        this.code = 200;
        this.msg = "请求成功";
        return this;
    }
}
