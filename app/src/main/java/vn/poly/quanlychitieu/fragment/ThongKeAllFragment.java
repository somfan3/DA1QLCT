package vn.poly.quanlychitieu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.poly.quanlychitieu.R;
import vn.poly.quanlychitieu.dao.ChiTieuDAO;
import vn.poly.quanlychitieu.dao.ThuNhapDAO;

import java.text.DecimalFormat;

public class ThongKeAllFragment extends Fragment {
    TextView tv_tongAll,tv_chiAll,tv_duAll;
    ThuNhapDAO thuNhapDAO;
    ChiTieuDAO chiTieuDAO;
    final DecimalFormat decimalFormat = new DecimalFormat("###,###.###");

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thong_ke_all, container, false);
        initView();
        thuNhapDAO = new ThuNhapDAO(getContext());
        chiTieuDAO = new ChiTieuDAO(getContext());

        tv_tongAll.setText(decimalFormat.format(thuNhapDAO.getTongThu()) + " VND");
        tv_chiAll.setText(decimalFormat.format(chiTieuDAO.getTongChi()) + " VND");
        tv_duAll.setText(decimalFormat.format((thuNhapDAO.getTongThu() - chiTieuDAO.getTongChi())) + " VND");

        return view ;
    }
    public void initView(){
        tv_tongAll = view.findViewById(R.id.tv_tongAll);
        tv_chiAll = view.findViewById(R.id.tv_chiAll);
        tv_duAll = view.findViewById(R.id.tv_duAll);
    }
}