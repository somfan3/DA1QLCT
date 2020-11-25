package com.anhntph10246.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ChiTieuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tieu);
        setupToolbar();
    }

    public void listLoaiChi(View view) {
        startActivity(new Intent(getApplicationContext(),ListLoaiChiActivity.class));
    }
    public void setupToolbar(){
        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolbarChiTieu);
        setSupportActionBar(toolbar);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Chi tiêu");
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}