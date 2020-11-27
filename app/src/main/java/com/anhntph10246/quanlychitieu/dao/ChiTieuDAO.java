package com.anhntph10246.quanlychitieu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anhntph10246.quanlychitieu.database.DatabaseHelper;
import com.anhntph10246.quanlychitieu.model.ChiTieu;
import com.anhntph10246.quanlychitieu.model.ThuNhap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChiTieuDAO {
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public static final String TABLE_NAME = "ChiTieu";
    public static final String COLUMN_MA_CHI = "machi";
    public static final String COLUMN_MA_LOAI_CHI = "maloaichi";
    public static final String COLUMN_TIEN_CHI = "tienchi";
    public static final String COLUMN_NGAY_CHI = "ngaychi";
    public static final String COLUMN_GHI_CHU_CHI = "ghichuchi";

    public static final String SQL_CHI_TIEU = "Create Table " + TABLE_NAME + "("
            + COLUMN_MA_CHI + " integer primary key autoincrement,"
            + COLUMN_MA_LOAI_CHI + " text,"
            + COLUMN_TIEN_CHI + " real,"
            + COLUMN_NGAY_CHI + " date,"
            + COLUMN_GHI_CHU_CHI + " text" +
            ",foreign key(maloaichi) references LoaiChi(maloaichi) on update cascade)";

    public ChiTieuDAO(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public int insertChiTieu(String loai, double tien, Date ngay, String ghichu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_CHI, loai);
        contentValues.put(COLUMN_TIEN_CHI, tien);
        contentValues.put(COLUMN_NGAY_CHI, sdf.format(ngay));
        contentValues.put(COLUMN_GHI_CHU_CHI, ghichu);

        try {
            if (db.insert(TABLE_NAME, null, contentValues) < 0) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }
    public int updateChiTieu(int ma, String loai, double tien, Date ngay, String ghichu){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_CHI, loai);
        contentValues.put(COLUMN_TIEN_CHI, tien);
        contentValues.put(COLUMN_NGAY_CHI, sdf.format(ngay));
        contentValues.put(COLUMN_GHI_CHU_CHI, ghichu);
        try {
            if (db.update(TABLE_NAME,contentValues,COLUMN_MA_CHI + " = ?",new String[]{String.valueOf(ma)}) < 0){
                return -1;
            }
        }catch (Exception e){
            return -1;
        }
        return 1;
    }
    public int deleteChiTIeu(int id) {
        if (db.delete(TABLE_NAME, COLUMN_MA_CHI + " = ?", new String[]{String.valueOf(id)}) < 0) {
            return -1;
        }
        return 1;
    }
    public List<ChiTieu> getAllChiTieu(){
        List<ChiTieu> chiTieuList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by " + COLUMN_NGAY_CHI + " DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ChiTieu chiTieu = null;
            try {
                chiTieu = new ChiTieu(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        sdf.parse(cursor.getString(3)),
                        cursor.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            chiTieuList.add(chiTieu);
            cursor.moveToNext();
        }
        cursor.close();

        return chiTieuList;
    }
    public int getIconChi(String maloai){
        String sql = "Select " + LoaiChiDAO.COLUMN_ICON_LOAI_CHI + " From " + LoaiChiDAO.TABLE_NAME
                + " Where " + LoaiChiDAO.COLUMN_MA_LOAI_CHI+ " = '" + maloai + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int icon = cursor.getInt(0);
        cursor.close();

        return icon;
    }
    public double getTongChi(){
        Double tongChi = 0.0;
        String sql = "Select Sum(" +COLUMN_TIEN_CHI  + ") From " +TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        tongChi = cursor.getDouble(0);
        cursor.close();
        return tongChi;
    }
    public double getTongChiNam(String nam){
        Double tongChi = 0.0;
        String sql = "Select Sum(" +COLUMN_TIEN_CHI  + ") From " +TABLE_NAME +" Where strftime('%Y'," + COLUMN_NGAY_CHI + ") " +
                " = '" + nam + "'";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        tongChi = cursor.getDouble(0);
        cursor.close();
        return tongChi;
    }
    public double getTongChiThang(String nam , String thang){
        if (Integer.parseInt(thang) < 10){
            thang = "0"+thang;
        }
        Double tongChi = 0.0;
        String sql = "Select Sum(" +COLUMN_TIEN_CHI  + ") From " +TABLE_NAME +" Where strftime('%Y'," + COLUMN_NGAY_CHI + ") " +
                " = '" + nam + "' And strftime('%m'," + COLUMN_NGAY_CHI + ") = '" + thang + "'" ;
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        tongChi = cursor.getDouble(0);
        cursor.close();
        return tongChi;
    }
}