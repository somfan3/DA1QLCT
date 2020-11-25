package com.anhntph10246.quanlychitieu.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anhntph10246.quanlychitieu.BieuDoActivity;
import com.anhntph10246.quanlychitieu.R;
import com.anhntph10246.quanlychitieu.adapter.ViewPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class ThongKeFragment extends Fragment {
    View view;
    ViewPager vpThongKe;
    TabLayout tlThongKe;
    FloatingActionButton fabBieuDo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        initView();


        fabBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BieuDoActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addTabs(vpThongKe);
        tlThongKe.setupWithViewPager(vpThongKe);
    }

    public void initView(){
        vpThongKe = view.findViewById(R.id.vpThongke);
        tlThongKe =  view.findViewById(R.id.tlThongke);
        fabBieuDo = view.findViewById(R.id.fabBieuDo);
    }
    public void addTabs(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ThongKeAllFragment(),"Tất cả");
        adapter.addFrag(new ThongKeThangFragment(),"Tháng");
        adapter.addFrag(new ThongKeNamFragment(),"Năm");
        viewPager.setAdapter(adapter);
    }
}