package com.example.bills;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ViewExistingBudget extends AppCompatActivity {
    //IT19097084 Ayodya Hettiarachchi

    //declare elements used
    private TextView totalExpValue, targetValue, budgetNameNav;
    private TextView remainderValue, remainderText;
    private Button addBtn, removeBtn, modifyBtn;
    private TextInputEditText addInput, removeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_existing_budget);

        //find declared elements
        //TextView
        totalExpValue = findViewById(R.id.totalExpValue); //needs to be updated
        targetValue = findViewById(R.id.targetValue); //constant unless if modified
        budgetNameNav = findViewById(R.id.budgetNameNav); //shows budget name
        remainderValue = findViewById(R.id.remainderValue); //needs to be updated
        remainderText = findViewById(R.id.remainderText); //changes IF total exceeds target
        //buttons
        addBtn = findViewById(R.id.addBtn);
        removeBtn = findViewById(R.id.removeButton);
        modifyBtn = findViewById(R.id.modifyBtn);
        //TextInputEditText
        addInput = findViewById(R.id.addInput);
        removeInput = findViewById(R.id.removeInput);

        //call functions when addBtn is tapped
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                add();
            }
        });

        //call functions when removeBtn is tapped
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reduce();
            }
        });

        //transition to next page when modifyBtn is tapped
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoModifyBudget();
            }
        });
    }

    //variables to convenience function operations
    float convertedTotalSpent;
    float convertedAddInput;
    float convertedRemoveInput;
    float convertedTarget;
    float convertedRemainder;

    //add value to expenses
    private void add() {
        //initialize to avoid errors
        convertedTotalSpent = 0;
        convertedAddInput = 0;
        convertedTarget = 0;
        convertedRemainder = 0;

        String spent = totalExpValue.getText().toString();
        convertedTotalSpent = Float.parseFloat(spent);
        String addThis = addInput.getText().toString();
        convertedAddInput = Float.parseFloat(addThis);
        String target = targetValue.getText().toString();
        convertedTarget = Float.parseFloat(target);
        String remains = remainderValue.getText().toString();
        convertedRemainder = Float.parseFloat(remains);

        //calculation
        convertedTotalSpent = convertedTotalSpent + convertedAddInput;

        //display calculated value
        totalExpValue.setText(String.valueOf(convertedTotalSpent));

        if (convertedTarget > convertedTotalSpent) {
            convertedRemainder = convertedTarget - convertedTotalSpent;
            remainderText.setText("Under Budget");
            remainderValue.setText(String.valueOf(convertedRemainder));
        } else if (convertedTotalSpent > convertedTarget) {
            convertedRemainder = convertedTotalSpent - convertedTarget;
            remainderText.setText("Over Budget");
            remainderValue.setText(String.valueOf(convertedRemainder));
        } else if (convertedTotalSpent == convertedTarget) {
            remainderText.setText("Target Reached");
            remainderValue.setText("0");
        }

        addInput.getText().clear();

        refresh();
    }

    private void reduce() {
        //initialize
        convertedTotalSpent = 0;
        convertedRemoveInput = 0;
        convertedTarget = 0;
        convertedRemainder = 0;

        String spent = totalExpValue.getText().toString();
        convertedTotalSpent = Float.parseFloat(spent);
        String subtractThis = removeInput.getText().toString();
        convertedRemoveInput = Float.parseFloat(subtractThis);
        String target = targetValue.getText().toString();
        convertedTarget = Float.parseFloat(target);
        String remains = remainderValue.getText().toString();
        convertedRemainder = Float.parseFloat(remains);

        //calculation
        convertedTotalSpent = convertedTotalSpent - convertedRemoveInput;

        //display calculated value
        totalExpValue.setText(String.valueOf(convertedTotalSpent));

        if (convertedTarget > convertedTotalSpent) {
            convertedRemainder = convertedTarget - convertedTotalSpent;
            remainderText.setText("Under Budget");
            remainderValue.setText(String.valueOf(convertedRemainder));
        } else if (convertedTotalSpent > convertedTarget) {
            convertedRemainder = convertedTotalSpent - convertedTarget;
            remainderText.setText("Over Budget");
            remainderValue.setText(String.valueOf(convertedRemainder));
        } else if (convertedTotalSpent == convertedTarget) {
            remainderText.setText("Target Reached");
            remainderValue.setText("0");
        }

        removeInput.getText().clear();

        refresh();
    }

    private void clearValues(){
        //function to clear user input on fields


    }

    private void gotoModifyBudget(){
        Intent i = new Intent(this, ModifyBudget.class);
        startActivity(i);
    }

    private void refresh(){
        finish();
        startActivity(getIntent());
    }
}