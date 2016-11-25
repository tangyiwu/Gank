package com.example.tyw.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tyw.gank.R;
import com.example.tyw.gank.adapter.GankAdapter;
import com.example.tyw.gank.http.BaseResponseCallback;
import com.example.tyw.gank.http.HttpManager;
import com.example.tyw.gank.model.Info;
import com.example.tyw.gank.model.InfoList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by tangyiwu on 2016/11/24.
 */

public class GankFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    public static final String FRAGMENT_TYPE = "fragment_type";
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView;
    private GankAdapter mAdapter;

    private String mType;

    private int mPageNum = 1;

    private List<Info> mList = new ArrayList<>();

    private FragmentActivity mContext;

    private boolean isRefreshing;

    public static Fragment newFragment(String type) {
        Fragment fragment = new GankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(FRAGMENT_TYPE);
        mContext = (FragmentActivity) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mListView = (ListView) view.findViewById(R.id.lv_info);
        mAdapter = new GankAdapter(mContext, mList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

        mRefreshLayout.setOnRefreshListener(this);

        mList.clear();
        getInfo();
    }

    private BaseResponseCallback<InfoList> callback = new BaseResponseCallback<InfoList>() {
        @Override
        public void parseResponse(Response<InfoList> response) {
            InfoList infoList = response.body();
            if (!infoList.error) {
                if (isRefreshing) {
                    mList.clear();
                    isRefreshing = false;
                }
                mList.addAll(infoList.results);
                mAdapter.notifyDataSetChanged();
            }

            mRefreshLayout.setRefreshing(false);
        }
    };

    private void getInfo() {
        HttpManager.getInstance().getInfo(mType, mPageNum, callback);
    }

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        getInfo();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Info info = mList.get(position);
        startActivity(WebActivity.buildIntent(mContext, info.desc, info.url));
    }
}
