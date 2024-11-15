package com.vuntph53431.lab3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    public static final String DB_NAME = "SanPham.db";
    public static final int DB_VERSION = 3;
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_CONTENT = "CONTENT";
    public static final String COLUMN_DATE = "DATE";
    public static final String TABLE_SANPHAM_NAME = "SANPHAM";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_STYLE = "STYLE";

    public Helper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_SANPHAM_NAME + " ("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_CONTENT + " TEXT NOT NULL, "
                + COLUMN_DATE + " TEXT NOT NULL, "
                + COLUMN_STYLE + " TEXT NOT NULL,"
                + COLUMN_STATUS + " INTEGER NOT NULL)";
        db.execSQL(sql);

        String insert = "INSERT INTO " + TABLE_SANPHAM_NAME + " VALUES (1, 'JAVA', 'JAVA2', '22/12/2022','Dễ', 1)";
        db.execSQL(insert);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SANPHAM_NAME);
            onCreate(db);
        }
    }
}
