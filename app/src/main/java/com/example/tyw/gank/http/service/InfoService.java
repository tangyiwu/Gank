package com.example.tyw.gank.http.service;

import com.example.tyw.gank.model.InfoList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tangyiwu on 2016/11/25.
 */

public interface InfoService {
    @GET("data/{type}/10/{pageNum}")
    Call<InfoList> listInfo(@Path("type") String type, @Path("pageNum") int pageNum);
}
