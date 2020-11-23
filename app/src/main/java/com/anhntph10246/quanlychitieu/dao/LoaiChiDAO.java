package com.anhntph10246.quanlychitieu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anhntph10246.quanlychitieu.database.DatabaseHelper;
import com.anhntph10246.quanlychitieu.model.LoaiChi;

import java.util.ArrayList;
import java.util.List;

public class LoaiChiDAO {
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "LoaiChi";
    public static final String COLUMN_MA_LOAI_CHI = "maloaichi";
    public static final String COLUMN_ICON_LOAI_CHI = "iconloaichi";
    public static final String SQL_LOAI_CHI = "Create Table " + TABLE_NAME + "("
            + COLUMN_MA_LOAI_CHI +" text primary key ,"
            + COLUMN_ICON_LOAI_CHI + " integer)";
    public LoaiChiDAO(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    //insert
    public int insertLoaiChi(LoaiChi loaiChi){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_CHI,loaiChi.getMaLoai());
        contentValues.put(COLUMN_ICON_LOAI_CHI,loaiChi.getIcon());

        if (db.insert(TABLE_NAME,null,contentValues) < 0){
            return -1;
        }
        return 1;
    }
    //get all
    public List<LoaiChi> getAllLoaiChi(){
        List<LoaiChi> LoaiChiList = new ArrayList<>();

        Cursor cursor = db.rawQuery("Select * From LoaiChi",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiChi loaiChi = new LoaiChi(cursor.getString(0),
                    cursor.getInt(1));
            LoaiChiList.add(loaiChi);
            cursor.moveToNext();
        }
        cursor.close();

        return LoaiChiList;
    }
    //update
    public int updateLoaiChi(LoaiChi loaiChi){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_CHI,loaiChi.getMaLoai());
        contentValues.put(COLUMN_ICON_LOAI_CHI,loaiChi.getIcon());
        if (db.insert(TABLE_NAME,null,contentValues) < 0){
            return -1;
        }
        return 1;
    }
    //delete
    public int deleteLoaiChi(int id){
        if (db.delete(TABLE_NAME,COLUMN_MA_LOAI_CHI+" = ?",new String[]{String.valueOf(id)}) < 0){
            return -1;
        }
        return 1;
    }
}
