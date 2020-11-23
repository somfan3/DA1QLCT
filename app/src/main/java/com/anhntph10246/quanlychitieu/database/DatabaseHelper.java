package com.anhntph10246.quanlychitieu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.anhntph10246.quanlychitieu.dao.LoaiThuDAO;
import com.anhntph10246.quanlychitieu.dao.ThuNhapDAO;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "qltc.db", null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LoaiThuDAO.SQL_LOAI_THU);
        sqLiteDatabase.execSQL(ThuNhapDAO.SQL_THU_NHAP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + LoaiThuDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table if exists " + ThuNhapDAO.TABLE_NAME);

    }
}
