package vn.poly.quanlychitieu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import vn.poly.quanlychitieu.R;
import vn.poly.quanlychitieu.dao.ThuNhapDAO;

public class BieuDoThuFragment extends Fragment {
    Button btn_bdthunam;
    PieChart chart_thu;
    ThuNhapDAO thuNhapDAO;
    View view;
    ArrayList<PieEntry> myPieData;

    String y = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bieu_do_thu, container, false);

        thuNhapDAO = new ThuNhapDAO(getActivity());
        initView();
        setupChart();

        btn_bdthunam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(),
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                y = String.valueOf(selectedYear);
                                if (thuNhapDAO.getMoneyInMoth(y).size() == 0){
                                    Toast.makeText(getContext(),"Năm " +y + " không có dữ liệu",Toast.LENGTH_SHORT).show();
                                }else {
                                    btn_bdthunam.setText(y);
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
        btn_bdthunam = view.findViewById(R.id.btn_bdthunam);
        chart_thu = view.findViewById(R.id.chart_thu);
    }
    public void setupChart(){
        myPieData = new ArrayList<>();

        PieDataSet pieDataSet = new PieDataSet(myPieData,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        chart_thu.setData(pieData);

        Legend legend = chart_thu.getLegend();
        legend.setTextSize(16f);


    }
    public void setDataChart(){
        myPieData.clear();
        for (int i = 0 ; i < thuNhapDAO.getMoth(y).size() ; i++){
            myPieData.add(new PieEntry(Float.parseFloat(String.valueOf(thuNhapDAO.getMoneyInMoth(y).get(i))),
                    "T" + thuNhapDAO.getMoth(y).get(i)));
        }
        chart_thu.notifyDataSetChanged();
        chart_thu.invalidate();
    }

}