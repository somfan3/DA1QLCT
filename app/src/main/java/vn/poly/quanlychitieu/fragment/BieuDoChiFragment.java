package vn.poly.quanlychitieu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;

import vn.poly.quanlychitieu.R;
import vn.poly.quanlychitieu.dao.ChiTieuDAO;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


public class BieuDoChiFragment extends Fragment {
    Button btn_bdchinam;
    PieChart chart_chi;
    ChiTieuDAO chiTieuDAO;
    View view;
    ArrayList<PieEntry> myPieData;


    String y = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bieu_do_chi, container, false);
        chiTieuDAO = new ChiTieuDAO(getActivity());
        initView();
        setupChart();


        btn_bdchinam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(),
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                y = String.valueOf(selectedYear);
                                if (chiTieuDAO.getMoneyInMoth(y).size() == 0){
                                    Toast.makeText(getContext(),"Năm " +y + " không có dữ liệu",Toast.LENGTH_SHORT).show();
                                }else {
                                    btn_bdchinam.setText(y);
                                    setDataChart();

                                }
                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH));

                builder.setMinYear(2015).
                        setActivatedYear(calendar.get(Calendar.YEAR)).
                        setMaxYear(2030).
                        setTitle("Select year").
                        showYearOnly()
                        .build().show();

            }
        });


        return view;
    }

    public void initView(){
        btn_bdchinam = view.findViewById(R.id.btn_bdchinam);
        chart_chi = view.findViewById(R.id.chart_chi);
    }


    public void setupChart(){
        myPieData = new ArrayList<>();

        PieDataSet pieDataSet = new PieDataSet(myPieData,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        chart_chi.setData(pieData);

        Legend legend = chart_chi.getLegend();
        legend.setTextSize(16f);

    }
    public void setDataChart(){
        myPieData.clear();
        for (int i = 0 ; i < chiTieuDAO.getMoth(y).size() ; i++){
            myPieData.add(new PieEntry(Float.parseFloat(String.valueOf(chiTieuDAO.getMoneyInMoth(y).get(i))),
                    "T" + chiTieuDAO.getMoth(y).get(i)));
        }
        chart_chi.notifyDataSetChanged();
        chart_chi.invalidate();
    }
}