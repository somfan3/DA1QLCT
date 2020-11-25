package com.anhntph10246.quanlychitieu.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.anhntph10246.quanlychitieu.NumberTextWatcherForThousand;
import com.anhntph10246.quanlychitieu.R;
import com.anhntph10246.quanlychitieu.ThuNhapActivity;
import com.anhntph10246.quanlychitieu.adapter.LoaiAdapter;
import com.anhntph10246.quanlychitieu.adapter.ThuNhapAdapter;
import com.anhntph10246.quanlychitieu.dao.LoaiThuDAO;
import com.anhntph10246.quanlychitieu.dao.ThuNhapDAO;
import com.anhntph10246.quanlychitieu.model.LoaiThu;
import com.anhntph10246.quanlychitieu.model.ThuNhap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TimThuNhapFragment extends Fragment {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Spinner spn_loaithu;
    ThuNhapAdapter thuNhapAdapter;
    RecyclerView rcv_thu;
    EditText edt_timthu;
    Button btn_ngaythustart,btn_ngaythuend,btn_timthu;
    ThuNhapDAO thuNhapDAO;
    LoaiThuDAO loaiThuDAO;
    List<LoaiThu> loaiThuList;
    List<ThuNhap> thuNhapList;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_thu_nhap, container, false);
        thuNhapDAO = new ThuNhapDAO(getContext());
        loaiThuDAO = new LoaiThuDAO(getContext());
        initView();
        getLoaiThu();
        numberFormat();
        chonNgayBatDau();
        chonNgayKetThuc();
        setupRecycleView();
        tim();
        return view;
    }
    public void initView(){
        spn_loaithu = view.findViewById(R.id.spn_loaithu);
        edt_timthu = view.findViewById(R.id.edt_timthu);
        btn_ngaythustart = view.findViewById(R.id.btn_ngaythustart);
        btn_ngaythuend = view.findViewById(R.id.btn_ngaythuend);
        rcv_thu = view.findViewById(R.id.rcv_thu);
        btn_timthu = view.findViewById(R.id.btn_timthu);
    }
    public void numberFormat(){
        edt_timthu.addTextChangedListener(new NumberTextWatcherForThousand(edt_timthu));
    }
    public void getLoaiThu(){
        loaiThuList = loaiThuDAO.getAllLoaiThu();
        loaiThuList.add(new LoaiThu("Chọn",R.mipmap.ic_launcher));
        LoaiAdapter loaiAdapter = new LoaiAdapter(loaiThuList,getContext());
        spn_loaithu.setAdapter(loaiAdapter);
        spn_loaithu.setSelection(loaiThuList.size()-1);
    }
    public void chonNgayBatDau(){
        btn_ngaythustart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i,i1,i2);
                        btn_ngaythustart.setText(sdf.format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    public void chonNgayKetThuc(){
        btn_ngaythuend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i,i1,i2);
                        btn_ngaythuend.setText(sdf.format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    public void setupRecycleView(){
        thuNhapList = new ArrayList<>();
        rcv_thu.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_thu.setHasFixedSize(true);
        thuNhapAdapter = new ThuNhapAdapter(getContext(),thuNhapList);
        rcv_thu.setAdapter(thuNhapAdapter);
    }
    public void tim(){
//        btn_timthu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                double tien = Double.parseDouble(edt_timthu.getText().toString().replace(",",""));
//                changeData(thuNhapDAO.tim(tien));
//            }
//        });
    }
    public void changeData(List<ThuNhap> lists){
        thuNhapList.clear();
        thuNhapList = lists;
        thuNhapAdapter.setDataChange(thuNhapList);
    }
}