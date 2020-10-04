package com.example.bills;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addBill extends AppCompatActivity {
    private EditText category, amount,dueDate;
    private Button add;
    private DBHandler dbHandler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        category = findViewById(R.id.category);
        amount = findViewById(R.id.amount);
        dueDate = findViewById(R.id.description);
        add = findViewById(R.id.buttonadd);
        context = this;

        dbHandler = new DBHandler(context);

        //when click on add bill button insert data into personalBill table
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from the form
                String billCategory = category.getText().toString();
                double billAmount = Double.parseDouble(amount.getText().toString());
                String  billDueDate = dueDate.getText().toString();
                //save data on the constructor
                personalBill pBill = new personalBill(billCategory,billAmount,billDueDate);
                //call the addBill method in DBHandler class and insert data into table
                dbHandler.addBill(pBill);
                //view inserted data in list view
                startActivity(new Intent(context,BillList.class));
            }
        });
    }
}