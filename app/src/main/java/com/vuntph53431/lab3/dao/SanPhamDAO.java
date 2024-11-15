package com.vuntph53431.lab3.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import com.vuntph53431.lab3.database.Helper;
import com.vuntph53431.lab3.model.SanPham;

public class SanPhamDAO {
    Helper helper;
    SQLiteDatabase database;
   public SanPhamDAO(Context context) {
        helper = new Helper(context);
        database = helper.getWritableDatabase();

    }

    @SuppressLint("Range")
    public ArrayList<SanPham> getSP(){
        ArrayList<SanPham> list = new ArrayList<>();
//        SQLiteDatabase database = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + Helper.TABLE_SANPHAM_NAME;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {
                SanPham sp = new SanPham();
                sp.setContent(cursor.getString(cursor.getColumnIndex(Helper.COLUMN_CONTENT)));
                sp.setTitle(cursor.getString(cursor.getColumnIndex(Helper.COLUMN_TITLE)));
                sp.setId(cursor.getInt(cursor.getColumnIndex(Helper.COLUMN_ID)));
                sp.setDate(cursor.getString(cursor.getColumnIndex(Helper.COLUMN_DATE)));
                sp.setStatus(cursor.getInt(cursor.getColumnIndex(Helper.COLUMN_STATUS)));
                sp.setStyle(cursor.getString(cursor.getColumnIndex(Helper.COLUMN_STYLE)));
                list.add(sp);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insertSP(SanPham sp){
        ContentValues values = new ContentValues();
        values.put(Helper.COLUMN_TITLE, sp.getTitle());
        values.put(Helper.COLUMN_CONTENT, sp.getContent());
        values.put(Helper.COLUMN_DATE, sp.getDate());
        values.put(Helper.COLUMN_STYLE, sp.getStyle());
        values.put(Helper.COLUMN_STATUS, sp.getStatus());
        long result = database.insert(Helper.TABLE_SANPHAM_NAME, null, values);
        return  result != -1;
    }
    public boolean updateSP(SanPham sp) {
        ContentValues values = new ContentValues();
        values.put(Helper.COLUMN_TITLE, sp.getTitle());
        values.put(Helper.COLUMN_CONTENT, sp.getContent());
        values.put(Helper.COLUMN_DATE, sp.getDate());
        values.put(Helper.COLUMN_STYLE, sp.getStyle());
        values.put(Helper.COLUMN_STATUS, sp.getStatus());
        int rowsAffected = database.update(Helper.TABLE_SANPHAM_NAME, values, Helper.COLUMN_ID + " = ?", new String[]{String.valueOf(sp.getId())});
        return rowsAffected > 0;
    }
    public boolean deleteSP(int id) {
        int rowsAffected = database.delete(Helper.TABLE_SANPHAM_NAME, Helper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }

    public Boolean updateStatus(SanPham pos) {
        ContentValues values = new ContentValues();
        values.put(Helper.COLUMN_STATUS, pos.getStatus());
        long row = database.update(Helper.TABLE_SANPHAM_NAME,values,"ID=?",new String[]{String.valueOf(pos.getId())});
        Log.d("UpdateStatus", "Rows affected: " + row);
        return row > 0;
    }
}
