package com.anhntph10246.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anhntph10246.quanlychitieu.adapter.LoaiChiSpnAdapter;
import com.anhntph10246.quanlychitieu.adapter.LoaiThuSpnAdapter;
import com.anhntph10246.quanlychitieu.dao.ChiTieuDAO;
import com.anhntph10246.quanlychitieu.dao.LoaiChiDAO;
import com.anhntph10246.quanlychitieu.model.LoaiChi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChiTieuActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    Calendar calendar;
    AppCompatSpinner spnLoaiChi;
    EditText edtTienChi, edtGhiChuChi;
    TextView tvNgayChi;
    ImageView ivThemLoaiChi, ivChonNgayChi;
    LoaiChiSpnAdapter adapter;
    List<LoaiChi> loaiChiList;
    LoaiChiDAO loaiChiDAO;
    ChiTieuDAO chiTieuDAO;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tieu);
        chiTieuDAO = new ChiTieuDAO(this);
        setupToolbar();
        initView();
        setupCurrentDate();
        getLoaiChi();
        numberFormat();
        getBundle();
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
    public void initView(){
        spnLoaiChi = findViewById(R.id.spnLoaiChi);
        edtTienChi = findViewById(R.id.edtTienChi);
        edtGhiChuChi = findViewById(R.id.edtGhiChuChi);
        tvNgayChi  = findViewById(R.id.tvNgayChi);
        ivThemLoaiChi = findViewById(R.id.ivThemLoaiChi);
        ivChonNgayChi = findViewById(R.id.ivChonNgayChi);
    }
    public void setupCurrentDate(){
        calendar = Calendar.getInstance();
        tvNgayChi.setText(sdf.format(calendar.getTime()));
    }
    public void getLoaiChi(){
        loaiChiDAO = new LoaiChiDAO(this);

        loaiChiList = new ArrayList<>();
        loaiChiList = loaiChiDAO.getAllLoaiChi();

        if (loaiChiList.size() < 1){
            LoaiChi loaichi1 = new LoaiChi("Tiền điện",R.drawable.ic_tien_dien);
            loaiChiList.add(loaichi1);
            loaiChiDAO.insertLoaiChi(loaichi1);
        }
        adapter = new LoaiChiSpnAdapter(loaiChiList,this);

        spnLoaiChi.setAdapter(adapter);
    }
    public void numberFormat(){
        edtTienChi.addTextChangedListener(new NumberTextWatcherForThousand(edtTienChi));
    }
    public void chonNgay(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ChiTieuActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                tvNgayChi.setText(sdf.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }
    public void getBundle(){
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null){
            edtTienChi.setText(decimalFormat.format(bundle.getDouble("tien")).replace(".",","));
            tvNgayChi.setText(bundle.getString("ngay"));
            edtGhiChuChi.setText(bundle.getString("ghichu"));
            spnLoaiChi.setSelection(checkPositionSpn(bundle.getString("loai")));
        }else{

        }
    }

    public void luu(View view) {
        if (bundle == null){
            if (validate() == -1){
                Toast.makeText(getApplicationContext(),"Không được để trống",Toast.LENGTH_SHORT).show();
            }else{
                String loai = loaiChiList.get(spnLoaiChi.getSelectedItemPosition()).getMaLoai();
                Double tien = Double.parseDouble(edtTienChi.getText().toString().replace(",",""));
                String ngay = tvNgayChi.getText().toString();
                String ghiChu = edtGhiChuChi.getText().toString().trim();
                try {
                    if (chiTieuDAO.insertChiTieu(loai,tien,sdf.parse(ngay),ghiChu) > 0){
                        finish();
                    }
                }catch (Exception e){

                }
            }
        }else{
            if (validate() == -1) {
                Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
            }else{
                int ma = bundle.getInt("ma");
                String loai = loaiChiList.get(spnLoaiChi.getSelectedItemPosition()).getMaLoai();
                Double tien = Double.parseDouble(edtTienChi.getText().toString().replace(",",""));
                String ngay = tvNgayChi.getText().toString();
                String ghiChu = edtGhiChuChi.getText().toString().trim();
                try {
                    if (chiTieuDAO.updateChiTieu(ma,loai,tien,sdf.parse(ngay),ghiChu) > 0){
                        finish();
                    }
                }catch (Exception e){

                }
            }
        }
    }

    public void huy(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaiChiList.clear();
        loaiChiList = loaiChiDAO.getAllLoaiChi();
        adapter.setDataChange(loaiChiList);
    }
    public int validate(){
        if (edtTienChi.getText().toString().trim().isEmpty()||
        edtGhiChuChi.getText().toString().trim().isEmpty()){
            return -1;
        }
        return 1;
    }
    public int checkPositionSpn(String loai){
        for (int i = 0 ; i < loaiChiList.size();i++){
            if (loai.equals(loaiChiList.get(i).getMaLoai())){
                return i;
            }
        }
        return -1;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete,menu);
        if (bundle == null){
            menu.findItem(R.id.item_delete).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_delete){
            if (chiTieuDAO.deleteChiTIeu(bundle.getInt("ma")) > 0){
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}