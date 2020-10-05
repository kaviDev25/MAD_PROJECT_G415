package com.example.budgeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class listBudgets extends AppCompatActivity {
    //IT19097084, Ayodya Hettiarachchi

    //declare elements used
    //public  BudgetListAdapter listAdapter;
    private FloatingActionButton createNewBudget;
    public static SQLiteDatabase budgetDatabase;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_budgets);

        RecyclerView recyclerView = findViewById(R.id.budgetsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //listAdapter = new BudgetListAdapter(this, getAllEntries());
        //recyclerView.setAdapter(listAdapter);

        //assign declared elements
        createNewBudget = findViewById(R.id.floatingActionButton);

        //start CreateNewBudget on button click
        createNewBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCreateNewBudget();
                //listAdapter.swapCursor(getAllEntries());
            }
        });
    }

    public void gotoCreateNewBudget(){
        Intent i = new Intent(this, CreateNewBudget.class);
        startActivity(i);
    }

    //get all (budget) items in table
    private Cursor getAllEntries(){
        return budgetDatabase.query(
                ShowBudgets.BudgetEntry.BUDGETS_LIST_TABLE_NAME,
                null, null, null, null, null,
                ShowBudgets.BudgetEntry.BUDGETS_COLUMN_TIMESTAMP + " DESC"
        );
    }
}
