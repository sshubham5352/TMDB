package com.app.manoranjan.retrofit;

public interface ApiResponseInterface {

    public void isError(String errorCode);
    public void isSuccess(Object response, int ServiceCode);
}
