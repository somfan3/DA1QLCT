package com.anhntph10246.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anhntph10246.quanlychitieu.adapter.IconLoaiAdapter;
import com.anhntph10246.quanlychitieu.dao.LoaiThuDAO;
import com.anhntph10246.quanlychitieu.dao.ThuNhapDAO;
import com.anhntph10246.quanlychitieu.model.LoaiThu;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ThemLoaiThuActivity extends AppCompatActivity {
    TextInputLayout textInputLoaiThu;
    ImageView ivIconThu, ivBack;
    GridView gvIcon;
    LoaiThuDAO loaiThuDAO;
    IconLoaiAdapter adapter;
    List<Integer> iconLoaiLists;
    Integer icon = null;
    Bundle bundle;
    String loai = "";
    ThuNhapDAO thuNhapDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_thu);
        thuNhapDAO = new ThuNhapDAO(this);
        loaiThuDAO = new LoaiThuDAO(this);

        initView();
        setupToolbar();
        setupIcon();
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null){
            loai = bundle.getString("loaithu");
            icon = bundle.getInt("icon");
            textInputLoaiThu.getEditText().setText(loai);
            ivIconThu.setImageResource(icon);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initView() {
        setTitle("");
        textInputLoaiThu = findViewById(R.id.textInputLoaiThu);
        ivIconThu = findViewById(R.id.ivIconThu);
        ivBack = findViewById(R.id.ivBack);
        gvIcon = findViewById(R.id.gvIconThu);
    }

    public void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarThemLoaiThu);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Loại thu nhập");
        setSupportActionBar(toolbar);
    }

    public int validate() {
        String loai = textInputLoaiThu.getEditText().getText().toString();
        if (loai.isEmpty()) {
            textInputLoaiThu.setError("Không được để trống");
            return -1;
        } else if (loai.length() > 15) {
            textInputLoaiThu.setError("Không được quá 15 ký tự");
            return -1;
        } else {
            textInputLoaiThu.setError(null);
        }
        return 1;
    }

    public void setupIcon() {
        iconLoaiLists = new ArrayList<>();
        iconLoaiLists.add(R.drawable.ic_luong);
        iconLoaiLists.add(R.drawable.ic_lich);
        iconLoaiLists.add(R.drawable.ic_suc_khoe);
        adapter = new IconLoaiAdapter(this, iconLoaiLists);
        gvIcon.setAdapter(adapter);
        gvIcon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                icon = iconLoaiLists.get(i);
                ivIconThu.setImageResource(icon);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_confirm) {
            if (bundle == null) {
                if (validate() == 1) {
                    if (icon == null) {
                        icon = iconLoaiLists.get(0);
                    }
                    if (loaiThuDAO.insertLoaiThu(new LoaiThu(textInputLoaiThu.getEditText().getText().toString().trim(),icon)) < 0){
                        Toast.makeText(this,"Loại thu đã tồn tại",Toast.LENGTH_SHORT).show();
                    }else{
                        finish();
                    }
                }
            }else {
                if (validate() == 1){
                    LoaiThu loaiThu = new LoaiThu(textInputLoaiThu.getEditText().getText().toString().trim(),
                            icon);
                    if (loaiThuDAO.updateLoaiThu(loaiThu,loai) < 0){
                        Toast.makeText(getApplicationContext(),"Loại thu đã tồn tại",Toast.LENGTH_SHORT).show();
                    }else{
                        if (checkUsedLoai(loai) == -1){
//                            thuNhapDAO.updateLoai(loai,loaiThu.getMaLoai());
                        }
                        finish();
                    }
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public int checkUsedLoai(String maloai){
        for (int i = 0 ; i < thuNhapDAO.getAllThuNhap().size() ; i++){
            if (maloai.equals(thuNhapDAO.getAllThuNhap().get(i).getMaLoai())){
                return -1; // used
            }
        }
        return 1;
    }
}