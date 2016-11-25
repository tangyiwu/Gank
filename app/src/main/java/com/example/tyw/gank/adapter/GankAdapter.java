package com.example.tyw.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tyw.gank.R;
import com.example.tyw.gank.model.Info;
import com.example.tyw.gank.ui.ImageFragment;

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
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_info_item, null);
            holder = new ViewHolder();
            holder.desc = (TextView) convertView.findViewById(R.id.tv_desc);
            holder.who = (TextView) convertView.findViewById(R.id.tv_who);
            holder.createAt = (TextView) convertView.findViewById(R.id.tv_created_at);
            holder.imagePager = (ViewPager) convertView.findViewById(R.id.image_viewPager);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Info info = getItem(position);
        holder.desc.setText(info.desc);
        holder.who.setText(info.who);
        holder.createAt.setText(formatDate(info.createdAt));
        if (info.images != null && info.images.length > 0) {
            holder.imagePager.setVisibility(View.VISIBLE);
            ImageFragmentPagerAdapter adapter = new ImageFragmentPagerAdapter(mContext.getSupportFragmentManager(), info.images);
            holder.imagePager.setAdapter(adapter);
        } else {
            holder.imagePager.setVisibility(View.GONE);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView desc;
        TextView who;
        TextView createAt;
        ViewPager imagePager;
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
}
