package com.example.bills;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.bills.ShowBudgets.BudgetEntry.BUDGETS_LIST_TABLE_NAME;

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION = 9;
    private static final String DB_NAME = "MobillsTracker";
    private static final String TABLE_NAME = "personalBill";//IT19218472
    private static final String IN_TABLE_NAME="Income_table";//IT19073156
    private static final String B_TABLE_NAME = "bills";//IT19753836

    //Column names of personalBill table IT19218472
    private static final String ID = "id";
    private static final String CATEGORY = "category";
    private static final String AMOUNT = "amount";
    private static final String DUEDATE = "dueDate";
    private static final String PAIDAMOUNT = "paidAmount";

    //IT19753836
    private static final String B_ID = "B_id";
    private static final String TYPE = "type";
    private static final String B_AMOUNT = "B_amount";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    //IT19073156
    private static final String col_1="id";
    private static final String col_2="year";
    private static final String col_3="month";
    private static final String col_4="income_type";
    private static final String col_5="income_amount";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null , VERSION);
    }
    @Override
    //create personalBill table IT19218472
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String TABLE_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " " + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CATEGORY + " TEXT,"
                + AMOUNT + " DOUBLE,"
                + DUEDATE + " TEXT,"
                + PAIDAMOUNT + " DOUBLE "
                + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY);

        //IT19753836
        String TABLE_CREATE_B = "CREATE TABLE " + B_TABLE_NAME + " " +
                "("
                + B_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TYPE + " TEXT,"
                + B_AMOUNT + " INTEGER,"
                + STARTED + " TEXT,"
                + FINISHED + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_B);

        //IT19097084
        final String SQL_CREATE_BUDGETLIST_TABLE = "CREATE TABLE " +
                BUDGETS_LIST_TABLE_NAME +"("+
                ShowBudgets.BudgetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ShowBudgets.BudgetEntry.AVAILABLE_BUDGETS_COLUMN  + " TEXT NOT NULL, " +
                ShowBudgets.BudgetEntry.BUDGETS_TARGET_SPENDING_COLUMN  + " TEXT NOT NULL," +
                ShowBudgets.BudgetEntry.BUDGET_NOTES  + " TEXT, " +
                ShowBudgets.BudgetEntry.BUDGETS_TOTAL_SPENT + " TEXT, " +
                ShowBudgets.BudgetEntry.BUDGETS_REMAINDER + " TEXT, " +
                ShowBudgets.BudgetEntry.BUDGETS_ADD_COLUMN + " TEXT, " +
                ShowBudgets.BudgetEntry.BUDGETS_REMOVE_COLUMN + " TEXT, " +
                ShowBudgets.BudgetEntry.BUDGETS_COLUMN_TIMESTAMP  + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "  +
                ");";
        //execute statement for database
        sqLiteDatabase.execSQL(SQL_CREATE_BUDGETLIST_TABLE);

        //IT19073156
        sqLiteDatabase.execSQL("create table "+IN_TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT, year TEXT,month TEXT,income_type TEXT,income_amount TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //IT19218472
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);

        String DROP_TABLE_B = "DROP TABLE IF EXISTS " + B_TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE_B);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BUDGETS_LIST_TABLE_NAME);
        onCreate(sqLiteDatabase);

        //IT19073156
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IN_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    //insert data into personalBill table IT19218472
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

    //retrieve all data from personalBill table and view on list IT19218472
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

    //update a bill IT19218472
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
    //delete bill of selected item in list view IT19218472
    public void deleteBill(int id){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,ID + " =?",new String[]{String.valueOf(id)}); // new string seen eken wenne id eka enne int,et db ekat id eka dilat iynne string ekat convert krgnnw
        sqLiteDatabase.close();
    }

    //IT19753836
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

    //IT19753836
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

    //IT19753836
    public int countBill() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + B_TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor.getCount();
    }

    //IT19753836
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

    //IT19753836
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

    //IT19073156
    public boolean insertData(String year,String month,String income_type, String income_amount){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_2,year);
        contentValues.put(col_3,month);
        contentValues.put(col_4,income_type);
        contentValues.put(col_5,income_amount);
        long result =db.insert(IN_TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }else
        {
            return true;
        }
    }

    //IT19073156
    public Cursor getallData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+IN_TABLE_NAME,null);
        return res;
    }

    //IT19073156
    public boolean updateData(String id,String year,String month,String income_type, String income_amount){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_1,id);
        contentValues.put(col_2,year);
        contentValues.put(col_3,month);
        contentValues.put(col_4,income_type);
        contentValues.put(col_5,income_amount);
        db.update(IN_TABLE_NAME,contentValues,"id=?",new String[]{id});
        return true;
    }

    //IT19073156
    public Integer deleteData(String id){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete(IN_TABLE_NAME,"ID=?",new String[]{id});

    }

}
