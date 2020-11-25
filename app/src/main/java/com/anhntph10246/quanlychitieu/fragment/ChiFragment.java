package com.anhntph10246.quanlychitieu.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anhntph10246.quanlychitieu.ChiTieuActivity;
import com.anhntph10246.quanlychitieu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ChiFragment extends Fragment {
    RecyclerView rcvChi;
    FloatingActionButton fabChi;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_chi, container, false);
        initView();

       fabChi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getContext(), ChiTieuActivity.class));
           }
       });

        return view;
    }
    public void initView(){
        rcvChi = view.findViewById(R.id.rcvChi);
        fabChi = view.findViewById(R.id.fabChi);
    }
}