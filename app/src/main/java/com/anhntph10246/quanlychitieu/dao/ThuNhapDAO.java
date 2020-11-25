package com.anhntph10246.quanlychitieu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anhntph10246.quanlychitieu.database.DatabaseHelper;
import com.anhntph10246.quanlychitieu.model.ThuNhap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThuNhapDAO {
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public static final String TABLE_NAME = "ThuNhap";
    public static final String COLUMN_MA_THU = "mathu";
    public static final String COLUMN_MA_LOAI_THU = "maloaithu";
    public static final String COLUMN_TIEN_THU = "tienthu";
    public static final String COLUMN_NGAY_THU = "ngaythu";
    public static final String COLUMN_GHI_CHU_THU = "ghichuthu";

    public static final String SQL_THU_NHAP = "Create Table " + TABLE_NAME + "("
            + COLUMN_MA_THU + " integer primary key autoincrement,"
            + COLUMN_MA_LOAI_THU + " text,"
            + COLUMN_TIEN_THU + " real,"
            + COLUMN_NGAY_THU + " date,"
            + COLUMN_GHI_CHU_THU + " text" +
            ",foreign key(maloaithu) references LoaiThu(maloaithu) on update cascade)";

    public ThuNhapDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int insertThuNhap(String loai, double tien, Date ngay, String ghichu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_THU, loai);
        contentValues.put(COLUMN_TIEN_THU, tien);
        contentValues.put(COLUMN_NGAY_THU, sdf.format(ngay));
        contentValues.put(COLUMN_GHI_CHU_THU, ghichu);

        try {
            if (db.insert(TABLE_NAME, null, contentValues) < 0) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    //update
    public int updateThuNhap(int ma, String loai, double tien, Date ngay, String ghichu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_THU, loai);
        contentValues.put(COLUMN_TIEN_THU, tien);
        contentValues.put(COLUMN_NGAY_THU, sdf.format(ngay));
        contentValues.put(COLUMN_GHI_CHU_THU, ghichu);

        try {
            if (db.update(TABLE_NAME, contentValues, COLUMN_MA_THU + " = ?", new String[]{String.valueOf(ma)}) < 0) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteThuNhap(int id) {
        if (db.delete(TABLE_NAME, COLUMN_MA_THU + " = ?", new String[]{String.valueOf(id)}) < 0) {
            return -1;
        }
        return 1;
    }

    //get all
    public List<ThuNhap> getAllThuNhap() {
        List<ThuNhap> thuNhapList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by " + COLUMN_NGAY_THU + " DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThuNhap thuNhap = null;
            try {
                thuNhap = new ThuNhap(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        sdf.parse(cursor.getString(3)),
                        cursor.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            thuNhapList.add(thuNhap);
            cursor.moveToNext();
        }
        cursor.close();

        return thuNhapList;
    }

    public int getIconThu(String ma) {
        String sql = "Select " + LoaiThuDAO.COLUMN_ICON_LOAI_THU + " From " + LoaiThuDAO.TABLE_NAME
                + " Where " + LoaiThuDAO.COLUMN_MA_LOAI_THU + " = '" + ma + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int icon = cursor.getInt(0);
        cursor.close();

        return icon;
    }


    public List<ThuNhap> tim(double tien) {
        List<ThuNhap> thuNhapList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_TIEN_THU + " > " + tien + " order by " + COLUMN_NGAY_THU + " DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThuNhap thuNhap = null;
            try {
                thuNhap = new ThuNhap(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        sdf.parse(cursor.getString(3)),
                        cursor.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            thuNhapList.add(thuNhap);
            cursor.moveToNext();
        }
        cursor.close();

        return thuNhapList;
    }
}

