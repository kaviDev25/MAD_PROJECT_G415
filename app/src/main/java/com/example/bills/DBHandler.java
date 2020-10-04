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

    private static final int VERSION = 7;
    private static final String DB_NAME = "MobillsTracker";
    private static final String TABLE_NAME = "personalBill";

    //Column names of personalBill table
    private static final String ID = "id";
    private static final String CATEGORY = "category";
    private static final String AMOUNT = "amount";
    private static final String DUEDATE = "dueDate";
    private static final String PAIDAMOUNT = "paidAmount";
    private static final String B_TABLE_NAME = "bills";
    private static final String B_ID = "B_id";
    private static final String TYPE = "type";
    private static final String B_AMOUNT = "B_amount";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

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
        String TABLE_CREATE_B = "CREATE TABLE " + B_TABLE_NAME + " " +
                "("
                + B_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TYPE + " TEXT,"
                + B_AMOUNT + " INTEGER,"
                + STARTED + " TEXT,"
                + FINISHED + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_B);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);

        String DROP_TABLE_B = "DROP TABLE IF EXISTS " + B_TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE_B);
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
    public List<personalBill> getAllBills() {
        List<personalBill> bills = new ArrayList();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {
                //create new bill object
                personalBill PBill = new personalBill();

                //get data
                PBill.setId(cursor.getInt(0));
                PBill.setCategory(cursor.getString(1));
                PBill.setAmount(cursor.getInt(2));
                PBill.setDueDate(cursor.getString(3));
                //bills = obj.objs.
                bills.add(PBill);
            } while (cursor.moveToNext());
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

    public void addBill(ModelClass model) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TYPE, model.getType());
        contentValues.put(B_AMOUNT, model.getAmount());
        contentValues.put(STARTED, model.getStarted());
        contentValues.put(FINISHED, model.getFinished());

        sqLiteDatabase.insert(B_TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }
    public void DeleteBill(int B_id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(B_TABLE_NAME, B_ID +" =?", new String[]{String.valueOf(B_id)});
        sqLiteDatabase.close();
    }
    public int updateBill(ModelClass model){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TYPE, model.getType());
        contentValues.put(B_AMOUNT, model.getAmount());
        contentValues.put(STARTED, model.getStarted());
        contentValues.put(FINISHED, model.getFinished());

        int status = sqLiteDatabase.update(B_TABLE_NAME,contentValues,B_ID + " =?",
                new String[]{String.valueOf(model.getId())});
        sqLiteDatabase.close();
        return status;

    }
    public int countBill() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + B_TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor.getCount();
    }

    public ModelClass GetSingleBill(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(B_TABLE_NAME, new String[]{B_ID, TYPE, B_AMOUNT,STARTED, FINISHED},
                B_ID + "= ?",new String[]{String.valueOf(id)},
                null, null, null, null);

        ModelClass models;
        if(cursor != null){
            cursor.moveToFirst();
            models = new ModelClass(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4)

            );
            return models;
        }
        return null;
    }

    List<ModelClass> GetAllBills() {
        List<ModelClass> model = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + B_TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ModelClass Model = new ModelClass();
                Model.setId(cursor.getInt(0));
                Model.setType(cursor.getString(1));
                Model.setAmount(cursor.getString(2));
                Model.setStarted(cursor.getLong(3));
                Model.setFinished(cursor.getLong(4));
                model.add(Model);
            } while (cursor.moveToNext());
        }
        return model;
    }

}
