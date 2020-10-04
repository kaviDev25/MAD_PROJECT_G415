package com.example.my_new_mad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.CaseMap;
import android.text.NoCopySpan;

import androidx.annotation.Nullable;


public class DbHandler extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DB_NAME="Income.db ";
    private static final String TABLE_NAME="Income_table";

    //coloumn names
    private static final String col_1="id";
    private static final String col_2="year";
    private static final String col_3="month";
    private static final String col_4="income_type";
    private static final String col_5="income_amount";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null,1 );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT, year TEXT,month TEXT,income_type TEXT,income_amount TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String year,String month,String income_type, String income_amount){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_2,year);
        contentValues.put(col_3,month);
        contentValues.put(col_4,income_type);
        contentValues.put(col_5,income_amount);
        long result =db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }else
            {
            return true;
        }
    }
    public Cursor getallData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public boolean updateData(String id,String year,String month,String income_type, String income_amount){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_1,id);
        contentValues.put(col_2,year);
        contentValues.put(col_3,month);
        contentValues.put(col_4,income_type);
        contentValues.put(col_5,income_amount);
        db.update(TABLE_NAME,contentValues,"id=?",new String[]{id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[]{id});

    }
}
