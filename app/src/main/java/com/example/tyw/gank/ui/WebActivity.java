package com.example.tyw.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.example.tyw.gank.R;
import com.example.tyw.gank.util.MeasureUtil;

/**
 * Created by tangyiwu on 2016/11/25.
 */

public class WebActivity extends AppCompatActivity {
    public static final String DESC = "desc";
    public static final String URL = "url";
    private Toolbar mToolbar;
    private WebView mWebView;

    public static Intent buildIntent(Context context, String desc, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(DESC, desc);
        intent.putExtra(URL, url);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWebView = (WebView) findViewById(R.id.webView);

        String desc = getIntent().getStringExtra(DESC);
        String url = getIntent().getStringExtra(URL);

        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        mToolbar.setTitleMarginStart(MeasureUtil.dp2px(this, 12));
        mToolbar.setTitle(desc);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.loadUrl(url);
    }
}
