package com.example.tyw.gank.http;

import com.example.tyw.gank.http.service.InfoService;
import com.example.tyw.gank.model.InfoList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tangyiwu on 2016/11/25.
 */

public class HttpManager {
    private static final String BASE_URL = "http://gank.io/api/";

    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 10;

    private HttpManager() {
    }

    private static HttpManager instance;

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    final static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            })
            .cookieJar(new CookieJar() {
                private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<HttpUrl, List<Cookie>>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(HttpUrl.parse(url.host()), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();

    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private Retrofit getRetrofit() {
        return retrofit;
    }

    public void getInfo(String type, int pageNum, final BaseResponseCallback<InfoList> callback) {
        InfoService service = getRetrofit().create(InfoService.class);
        Call<InfoList> call = service.listInfo(type, pageNum);
        call.enqueue(new Callback<InfoList>() {
            @Override
            public void onResponse(Call<InfoList> call, Response<InfoList> response) {
                if (response != null && callback != null) {
                    callback.parseResponse(response);
                }
            }

            @Override
            public void onFailure(Call<InfoList> call, Throwable t) {

            }
        });
    }
}
