package com.app.tmdb.retrofit;

public interface ApiResponseInterface {

    void apiCallFailure(String errorCode);

    void apiCallSuccess(Object response, int ServiceCode);
}
