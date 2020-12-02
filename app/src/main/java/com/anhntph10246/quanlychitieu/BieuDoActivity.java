package com.anhntph10246.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anhntph10246.quanlychitieu.adapter.ViewPagerAdapter;
import com.anhntph10246.quanlychitieu.fragment.BieuDoChiFragment;
import com.anhntph10246.quanlychitieu.fragment.BieuDoThuFragment;
import com.google.android.material.tabs.TabLayout;

public class BieuDoActivity extends AppCompatActivity {
    ViewPager vp_bieudo;
    TabLayout tl_bieudo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bieu_do);
        setupToolbar();
        initView();
        addTabs(vp_bieudo);
        tl_bieudo.setupWithViewPager(vp_bieudo);



    }

    public void setupToolbar(){
        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolbar_bieuDo);
        setSupportActionBar(toolbar);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Biểu đồ");
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void initView(){
        vp_bieudo = findViewById(R.id.vp_bieudo);
        tl_bieudo = findViewById(R.id.tl_bieudo);
    }
    public void addTabs(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BieuDoThuFragment(),"Thu nhập");
        adapter.addFrag(new BieuDoChiFragment(),"Chi tiêu");
        viewPager.setAdapter(adapter);

    }
}