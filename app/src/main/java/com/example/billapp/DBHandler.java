package com.example.billapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

public class DBHandler extends SQLiteOpenHelper {
    private static final int VERSION = 5;
    private static final String DB_NAME = "bills";
    private static final String TABLE_NAME = "bills";
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String AMOUNT = "amount";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " " +
                "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TYPE + " TEXT,"
                + AMOUNT + " INTEGER,"
                + STARTED + " TEXT,"
                + FINISHED + " TEXT" +
                ");";
        db.execSQL(TABLE_CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

    public void addBill(ModelClass model) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TYPE, model.getType());
        contentValues.put(AMOUNT, model.getAmount());
        contentValues.put(STARTED, model.getStarted());
        contentValues.put(FINISHED, model.getFinished());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }
    public void DeleteBill(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID +" =?", new String[]{String.valueOf(id)});
        db.close();
    }
    public int updateBill(ModelClass model){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TYPE, model.getType());
        contentValues.put(AMOUNT, model.getAmount());
        contentValues.put(STARTED, model.getStarted());
        contentValues.put(FINISHED, model.getFinished());

        int status = sqLiteDatabase.update(TABLE_NAME,contentValues,ID + " =?",
                new String[]{String.valueOf(model.getId())});
        sqLiteDatabase.close();
        return status;

    }
    public int countBill() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public ModelClass getSingleBill(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, TYPE, AMOUNT,STARTED, FINISHED},
                ID + "= ?",new String[]{String.valueOf(id)},
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

    public List<ModelClass> getAllBills() {
        List<ModelClass> model = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

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


