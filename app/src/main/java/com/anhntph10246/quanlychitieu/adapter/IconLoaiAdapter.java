package com.anhntph10246.quanlychitieu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.anhntph10246.quanlychitieu.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class IconLoaiAdapter extends BaseAdapter {
    Context context;
    List<Integer> iconLoaiThuLists;

    public IconLoaiAdapter(Context context, List<Integer> iconLoaiThuLists) {
        this.context = context;
        this.iconLoaiThuLists = iconLoaiThuLists;
    }

    @Override
    public int getCount() {
        return iconLoaiThuLists.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class IconThuHolder{
        ImageView ivChonIcon;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IconThuHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_icon_loai,viewGroup,false);

            holder = new IconThuHolder();
            holder.ivChonIcon = view.findViewById(R.id.ivChonIcon);

            view.setTag(holder);
        }else{
            holder = (IconThuHolder) view.getTag();
        }
        Integer icon = iconLoaiThuLists.get(i);

//        holder.ivChonIconThu.setImageResource(icon);

        Glide.with(context).load(icon).into(holder.ivChonIcon); //
        return view;
    }
}
