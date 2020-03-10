package com.sug.minifinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_new extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userinfo_.db";
    private static final String TABLE_NAME = "userinfo_";
    private static final String COLUMN_1 = "name";
    private static final String COLUMN_2 = "email";
    private static final String COLUMN_3 = "phone";
    private static final String COLUMN_4 = "address";
    private static final String COLUMN_5 = "aadhar";
    private static final String COLUMN_6 = "bt";

    public DB_new(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +"(" + COLUMN_1 + " varchar2 , " + COLUMN_2 + " varchar2 primary key, " + COLUMN_3 + " varchar2, "
                + COLUMN_4 + " varchar2, " + COLUMN_5 + " varchar2, " + COLUMN_6 + " varchar2) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean InsertData(String name, String email, String phone, String address, String aadhar, String bt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COLUMN_1,name);
        c.put(COLUMN_2,email);
        c.put(COLUMN_3,phone);
        c.put(COLUMN_4,address);
        c.put(COLUMN_5,aadhar);
        c.put(COLUMN_6,bt);
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
    public Integer deleteData(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "email = ?", new String[] {email});
    }
}
