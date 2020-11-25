package com.anhntph10246.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BieuDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bieu_do);
        setupToolbar();
    }
    public void setupToolbar(){
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
}