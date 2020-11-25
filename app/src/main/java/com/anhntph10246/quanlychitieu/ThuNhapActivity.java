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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anhntph10246.quanlychitieu.adapter.LoaiAdapter;
import com.anhntph10246.quanlychitieu.dao.LoaiThuDAO;
import com.anhntph10246.quanlychitieu.dao.ThuNhapDAO;
import com.anhntph10246.quanlychitieu.model.LoaiThu;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ThuNhapActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    Calendar calendar;
    AppCompatSpinner spnLoaiThu;
    EditText edtTienThu, edtGhiChuThu;
    TextView tvNgayThu;
    ImageView ivThemLoaiThu, ivChonNgayThu;
    LoaiAdapter loaiAdapter;
    List<LoaiThu> loaiThuList;
    LoaiThuDAO loaiThuDAO;
    Bundle bundle;
    ThuNhapDAO thuNhapDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_nhap);
        thuNhapDAO = new ThuNhapDAO(this);
        initView();
        setupToolbar();
        setupCurrentDate();
        numberFormat();
        getLoaiThu();
        getBundle();

    }

    public void initView(){
        spnLoaiThu = findViewById(R.id.spnLoaiThu);
        edtTienThu = findViewById(R.id.edtTienThu);
        edtGhiChuThu = findViewById(R.id.edtGhiChuThu);
        tvNgayThu  = findViewById(R.id.tvNgayThu);
        ivThemLoaiThu = findViewById(R.id.ivThemLoaiThu);
        ivChonNgayThu = findViewById(R.id.ivChonNgayThu);
    }
    public void setupToolbar(){
        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolbarThuNhap);
        setSupportActionBar(toolbar);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Chi Tiêu");
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void setupCurrentDate(){
        calendar = Calendar.getInstance();
        tvNgayThu.setText(sdf.format(calendar.getTime()));
    }
    public void getLoaiThu(){
        loaiThuDAO = new LoaiThuDAO(this);

        loaiThuList = loaiThuDAO.getAllLoaiThu();

        if (loaiThuList.size() < 1){
            LoaiThu loaiThu1 = new LoaiThu("Tiền lương",R.drawable.ic_luong);
            loaiThuList.add(loaiThu1);
            loaiThuDAO.insertLoaiThu(loaiThu1);
        }

        loaiAdapter = new LoaiAdapter(loaiThuList,this);
        spnLoaiThu.setAdapter(loaiAdapter);
    }
    public void luu(View view) {
        if (bundle == null){
            if (validate() == -1){
                Toast.makeText(getApplicationContext(),"Không được để trống",Toast.LENGTH_SHORT).show();
            }else{
                String loai = loaiThuList.get(spnLoaiThu.getSelectedItemPosition()).getMaLoai();
                Double tien =   Double.parseDouble(edtTienThu.getText().toString().replace(",",""));
                String ngay = tvNgayThu.getText().toString();
                String ghiChu = edtGhiChuThu.getText().toString().trim();
                try {
                    if (thuNhapDAO.insertThuNhap(loai,tien,sdf.parse(ngay),ghiChu) > 0){
                        finish();
                    }
                }catch (Exception e){
                }
            }
        }else{
            if (validate() == -1){
                Toast.makeText(getApplicationContext(),"Không được để trống",Toast.LENGTH_SHORT).show();
            }else{
                int ma = bundle.getInt("ma");
                String loai = loaiThuList.get(spnLoaiThu.getSelectedItemPosition()).getMaLoai();
                Double tien =   Double.parseDouble(edtTienThu.getText().toString().replace(",",""));
                String ngay = tvNgayThu.getText().toString();
                String ghiChu = edtGhiChuThu.getText().toString().trim();
                try {
                    if (thuNhapDAO.updateThuNhap(ma,loai,tien,sdf.parse(ngay),ghiChu) > 0){
                        finish();
                    }
                }catch (Exception e){

                }
            }
        }
    }
    public void getBundle(){
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null){
            edtTienThu.setText(decimalFormat.format(bundle.getDouble("tien")).replace(".",","));
            tvNgayThu.setText(bundle.getString("ngay"));
            edtGhiChuThu.setText(bundle.getString("ghichu"));
            spnLoaiThu.setSelection(checkPositionSpn(bundle.getString("loai")));
        }else{

        }

    }
    public void numberFormat(){
        edtTienThu.addTextChangedListener(new NumberTextWatcherForThousand(edtTienThu));
    }

    public void huy(View view) {
        finish();
    }

    public void chonNgay(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ThuNhapActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                tvNgayThu.setText(sdf.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }

    public void listLoaiThu(View view) {
        startActivity(new Intent(getApplicationContext(),ListLoaiThuActivity.class));
    }
    public int validate(){
        if (edtTienThu.getText().toString().isEmpty()||
                edtGhiChuThu.getText().toString().isEmpty()){
            return -1;
        }
        return 1;
    }
    @Override
    protected void onResume() {
        super.onResume();
        loaiThuList.clear();
        loaiThuList = loaiThuDAO.getAllLoaiThu();
        loaiAdapter.setDataChange(loaiThuList);
    }
    public int checkPositionSpn(String loai){
        for (int i = 0 ; i < thuNhapDAO.getAllThuNhap().size();i++){
            if (loai.equals(thuNhapDAO.getAllThuNhap().get(i).getMaLoai())){
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
            if (thuNhapDAO.deleteThuNhap(bundle.getInt("ma")) > 0){
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}