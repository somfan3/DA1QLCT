package com.anhntph10246.quanlychitieu.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.anhntph10246.quanlychitieu.R;
import com.anhntph10246.quanlychitieu.dao.ChiTieuDAO;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.List;


public class BieuDoChiFragment extends Fragment {
    AppCompatSpinner spn_nam;
    AnyChartView chart;
    final String[] nam = {"2018","2019","2020","2021","2022","2023"};
    ChiTieuDAO chiTieuDAO;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bieu_do_chi, container, false);
        chiTieuDAO = new ChiTieuDAO(getContext());
        initView();
        setupSpn();
        setupChart();
        String y = nam[spn_nam.getSelectedItemPosition()];
        pie.data(dataEntryList(chiTieuDAO.getMoth(y),chiTieuDAO.getMoneyInMoth(y)));
        return view;
    }

    public void initView(){
        spn_nam = view.findViewById(R.id.spn_bdchinam);
        chart = view.findViewById(R.id.chart_chi);
    }
    public void setupSpn(){
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,nam);
        spn_nam.setAdapter(arrayAdapter);
        spn_nam.setSelection(2);

    }
    public List<DataEntry> dataEntryList(List<String> thangList, List<Double> tienList){
        List<DataEntry> dataEntryList;
        dataEntryList= new ArrayList<>();
        for (int i = 0 ; i < thangList.size() ; i++){
            dataEntryList.add(new ValueDataEntry("T1",3212321));
        }
        return dataEntryList;
    }
    Pie pie;
    public void setupChart(){
        pie = AnyChart.pie();

        pie.labels().position("outside");


        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Tháng")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        chart.setChart(pie);
    }
}