package com.example.bills;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ModifyBudget extends AppCompatActivity {
    //IT19097084, Ayodya Hettiarachchi

    //declare elements used
    private TextInputEditText changeBudgetName, changeBudgetAmount;
    private Button applyChanges, deleteBudget;
    private TextView currentName, currentTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_budget);

        //find declared elements
        //TextInputEditTexts
        changeBudgetName = findViewById(R.id.changeBudgetName);
        changeBudgetAmount = findViewById(R.id.changeBudgetAmount);
        //Buttons
        applyChanges = findViewById(R.id.applyChanges);
        deleteBudget = findViewById(R.id.deleteBudget);
        //TextView
        currentName = findViewById(R.id.currentName);
        currentTarget = findViewById(R.id.currentTarget);

        //call functions when button is tapped
        applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoViewExistingBudget();
            }
        });
        deleteBudget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotoListBudgets();
            }
        });
    }

    //function enabling navigation to view existing budgets
    public void gotoViewExistingBudget(){
        Intent i = new Intent(this, ViewExistingBudget.class);
        startActivity(i);
    }

    //function enabling navigation to main list of budgets when deleted
    public void gotoListBudgets() {
        Intent i = new Intent(this, listBudgets.class);
        startActivity(i);
    }
}
