package com.example.budgeting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.example.budgeting.ShowBudgets.*;

import static com.example.budgeting.ShowBudgets.BudgetEntry.*;

public class DatabaseHandler extends SQLiteOpenHelper{
    //IT19097084 Ayodya Hettiarachchi

    public static final String DATABASE_NAME = "mobills.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_BUDGETLIST_TABLE = " CREATE TABLE " +
                BUDGETS_LIST_TABLE_NAME + " (" +
                BudgetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BudgetEntry.AVAILABLE_BUDGETS_COLUMN  + " TEXT NOT NULL, " +
                BudgetEntry.BUDGETS_TARGET_SPENDING_COLUMN  + " TEXT NOT NULL," +
                BudgetEntry.BUDGET_NOTES  + " TEXT, " +
                BudgetEntry.BUDGETS_TOTAL_SPENT + " TEXT, " +
                BudgetEntry.BUDGETS_REMAINDER + " TEXT, " +
                BudgetEntry.BUDGETS_ADD_COLUMN + " TEXT, " +
                BudgetEntry.BUDGETS_REMOVE_COLUMN + " TEXT, " +
                BudgetEntry.BUDGETS_COLUMN_TIMESTAMP  + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "  +
                ");";

        //execute statement for database
        sqLiteDatabase.execSQL(SQL_CREATE_BUDGETLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BUDGETS_LIST_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
