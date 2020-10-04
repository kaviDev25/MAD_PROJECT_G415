package com.example.bills;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "MobillsTracker";
    private static final String TABLE_NAME = "personalBill";

    //Column names of personalBill table
    private static final String ID = "id";
    private static final String CATEGORY = "category";
    private static final String AMOUNT = "amount";
    private static final String DUEDATE = "dueDate";
    private static final String PAIDAMOUNT = "paidAmount";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null , VERSION);
    }
    @Override
    //create personalBill table
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String TABLE_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " " + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CATEGORY + " TEXT,"
                + AMOUNT + " DOUBLE,"
                + DUEDATE + " TEXT,"
                + PAIDAMOUNT + " DOUBLE "
                + ");";

        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;

        //Drop older table if existed
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);

        //Create tables again
        onCreate(sqLiteDatabase);

    }

    //insert data into personalBill table
    public void addBill(personalBill pBill) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //insert data into personalBill table
        contentValues.put(CATEGORY, pBill.getCategory());
        contentValues.put(AMOUNT, pBill.getAmount());
        contentValues.put(DUEDATE, pBill.getDueDate());

        //save to table
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        //connection close coz more connection can be inturrupts
        sqLiteDatabase.close();
    }

    //retrieve all data from personalBill table and view on list
    public List<personalBill> getAllBills()
    {
        List<personalBill> bills = new ArrayList();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){

            do{
                //create new bill object
                personalBill PBill = new personalBill();

                //get data
                PBill.setId(cursor.getInt(0));
                PBill.setCategory(cursor.getString(1));
                PBill.setAmount(cursor.getInt(2));
                PBill.setDueDate(cursor.getString(3));
                //bills = obj.objs.
                bills.add(PBill);
            }while(cursor.moveToNext());
        }
        return bills;
    }

    //get single bill
    public personalBill getSingleBill(int id){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,new String[]{ID,CATEGORY,AMOUNT,DUEDATE,PAIDAMOUNT},
                ID + "= ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        personalBill personalbill;

        if(cursor != null){
            cursor.moveToFirst();
            personalbill  = new personalBill(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getString(3),
                    cursor.getDouble(4)
            );
            return personalbill;
        }
        return null;
    }

    //update a bill
    public int updateSingleBill(personalBill personalb){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CATEGORY,personalb.getCategory());
        contentValues.put(AMOUNT,personalb.getAmount());
        contentValues.put(DUEDATE,personalb.getDueDate());
        contentValues.put(PAIDAMOUNT,personalb.getPaidAmount());

        int status = sqLiteDatabase.update(TABLE_NAME,contentValues,ID + " =?",new String[]{String.valueOf(personalb.getId())});

        sqLiteDatabase.close();

        return status;

    }
    //delete bill of selected item in list view
    public void deleteBill(int id){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,ID + " =?",new String[]{String.valueOf(id)}); // new string seen eken wenne id eka enne int,et db ekat id eka dilat iynne string ekat convert krgnnw
        sqLiteDatabase.close();
    }
}
