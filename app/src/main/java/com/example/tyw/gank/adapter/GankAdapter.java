package com.example.tyw.gank.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tyw.gank.R;
import com.example.tyw.gank.model.Info;
import com.example.tyw.gank.ui.ImageFragment;
import com.example.tyw.gank.util.MeasureUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tangyiwu on 2016/11/24.
 */

public class GankAdapter extends BaseAdapter {
    private FragmentActivity mContext;
    private List<Info> mList;

    private OnLoadMoreListener mListener;

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mListener = listener;
    }

    private LayoutInflater mInflater;

    public GankAdapter(FragmentActivity context, List<Info> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Info getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_info_item, null);
            holder = new ViewHolder();
            holder.desc = (TextView) convertView.findViewById(R.id.tv_desc);
            holder.who = (TextView) convertView.findViewById(R.id.tv_who);
            holder.createAt = (TextView) convertView.findViewById(R.id.tv_created_at);
            holder.imagePager = (ViewPager) convertView.findViewById(R.id.image_viewPager);
            holder.bottomView = convertView.findViewById(R.id.rl_bottom);
            holder.dotContainer = (LinearLayout) convertView.findViewById(R.id.linear_dot_container);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Info info = getItem(position);
        holder.desc.setText(info.desc);
        holder.who.setText(info.who);
        holder.createAt.setText(formatDate(info.createdAt));
        if (info.images != null && info.images.length > 0) {
            holder.imagePager.setVisibility(View.VISIBLE);
            ImageFragmentPagerAdapter adapter = new ImageFragmentPagerAdapter(mContext.getSupportFragmentManager(), info.images);
            holder.imagePager.setAdapter(adapter);
            setBottomViewBackground(holder.bottomView);
            setDotsView(holder.dotContainer, info.images.length, 0);
            holder.imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    setDotsView(holder.dotContainer, info.images.length, position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            holder.imagePager.setVisibility(View.GONE);
            holder.bottomView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }
        if (position == getCount() - 1 && mListener != null) {
            mListener.onLoadMore();
        }
        return convertView;
    }

    private void setDotsView(LinearLayout parent, int count, int select) {
        parent.removeAllViews();
        if (count == 1) {
            return;
        }
        final int width = MeasureUtil.dp2px(parent.getContext(), 6);
        int leftMargin = MeasureUtil.dp2px(parent.getContext(), 8);
        for (int i = 0; i < count; i++) {
            View view = new View(parent.getContext());
            if (i == select) {
                view.setBackgroundResource(R.drawable.shape_select_dot_bg);
            } else {
                view.setBackgroundResource(R.drawable.shape_normal_dot_bg);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            if (i != 0) {
                params.leftMargin = leftMargin;
            }
            view.setLayoutParams(params);
            parent.addView(view);
        }
    }

    private void setBottomViewBackground(View view) {
        view.setBackgroundColor(Color.argb((int) (255 * 0.42), 0, 0, 0));
    }

    private static class ViewHolder {
        TextView desc;
        TextView who;
        TextView createAt;
        ViewPager imagePager;
        View bottomView;
        LinearLayout dotContainer;
    }

    private class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
        private String[] images;

        public ImageFragmentPagerAdapter(FragmentManager fm, String[] images) {
            super(fm);
            this.images = images;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newFragment(images[position]);
        }

        @Override
        public int getCount() {
            return images != null ? images.length : 0;
        }
    }

    private String formatDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
        try {
            return format.format(new Date(format.parse(date).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
