package com.sug.minifinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bb_.db";
    private static final String TABLE_NAME = "bb_";
    private static final String COLUMN_1 = "name";
    private static final String COLUMN_2 = "ap";
    private static final String COLUMN_3 = "an";
    private static final String COLUMN_4 = "bp";
    private static final String COLUMN_5 = "bn";
    private static final String COLUMN_6 = "abp";
    private static final String COLUMN_7 = "abn";
    private static final String COLUMN_8 = "op";
    private static final String COLUMN_9 = "one";

    public BB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +"(" + COLUMN_1 + " varchar2 primary key, " + COLUMN_2 + " varchar2, " + COLUMN_3 + " varchar2, "
                + COLUMN_4 + " varchar2, " + COLUMN_5 + " varchar2, " + COLUMN_6 + " varchar2, " + COLUMN_7 + " varchar2, " + COLUMN_8 + " varchar2, "
                + COLUMN_9 + " varchar2) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean InsertData(String name, String ap, String an, String bp, String bn, String abp, String abn, String op, String on)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COLUMN_1,name);
        c.put(COLUMN_2,ap);
        c.put(COLUMN_3,an);
        c.put(COLUMN_4,bp);
        c.put(COLUMN_5,bn);
        c.put(COLUMN_6,abp);
        c.put(COLUMN_7,abn);
        c.put(COLUMN_8,op);
        c.put(COLUMN_9,on);

        long res = db.insert(TABLE_NAME, null, c);
        db.close();
        if(res == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor r = db.rawQuery("select * from " + TABLE_NAME, null);
        return r;
    }
}
