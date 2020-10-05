package com.example.budgeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.budgeting.R;
import com.example.budgeting.ShowBudgets;
import com.example.budgeting.ViewExistingBudget;
import com.example.budgeting.listBudgets;
import com.google.android.material.textfield.TextInputEditText;


public class CreateNewBudget extends AppCompatActivity {
    //IT19097084 Ayodya Hettiarachchi

    //declare elements used
    private TextInputEditText budgetNameField, budgetAmountField, budgetNotes;
    private Button confirmBudgetCreation;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_budget);

        //using getwritabledatabase() crashes app
        //DatabaseHandler dbHandler = new DatabaseHandler(this);
        //budgetDatabase = dbHandler.getWritableDatabase();

        //find declared elements
        //TextInputEditText
        budgetNameField = findViewById(R.id.bNameInput);
        budgetAmountField = findViewById(R.id.budgetAmountField);
        budgetNotes = findViewById(R.id.budgetNotes);
        //button
        confirmBudgetCreation = findViewById(R.id.confirmBudgetCreation);

        confirmBudgetCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewBudget();
                gotoViewExistingBudgets();
            }
        });
    }

    private void createNewBudget() {
        //check for empty elements
        if (budgetNameField.getText().toString().trim().length() == 0 || budgetAmountField.getText().toString().trim().length() == 0) {
            return;
        }

        String name = budgetNameField.getText().toString();
        String target = budgetAmountField.getText().toString();
        String notes = budgetNotes.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(ShowBudgets.BudgetEntry.AVAILABLE_BUDGETS_COLUMN, name);
        cv.put(ShowBudgets.BudgetEntry.BUDGETS_TARGET_SPENDING_COLUMN, target);
        cv.put(ShowBudgets.BudgetEntry.BUDGET_NOTES, notes);
        cv.put(ShowBudgets.BudgetEntry.BUDGETS_TOTAL_SPENT, "0"); //Set values on create to 0
        cv.put(ShowBudgets.BudgetEntry.BUDGETS_REMAINDER, "0");
        cv.put(ShowBudgets.BudgetEntry.BUDGETS_ADD_COLUMN, "0");
        cv.put(ShowBudgets.BudgetEntry.BUDGETS_REMOVE_COLUMN, "0");

        //insert values into database
        //listBudgets.budgetDatabase.insert(ShowBudgets.BudgetEntry.BUDGETS_LIST_TABLE_NAME, null, cv);


        //clear user input on edit text fields
        budgetNameField.getText().clear();
        budgetAmountField.getText().clear();
        budgetNotes.getText().clear();
    }

    public void gotoViewExistingBudgets() {
        //navigate to view existing budget page
        Intent i = new Intent(this, ViewExistingBudget.class);
        startActivity(i);
    }
}