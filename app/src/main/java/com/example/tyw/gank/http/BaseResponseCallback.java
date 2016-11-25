package com.example.tyw.gank.http;

import retrofit2.Response;

/**
 * Created by tangyiwu on 2016/11/25.
 */

public interface BaseResponseCallback<T> {
    void parseResponse(Response<T> response);
}
