package com.example.bills;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class detailsView extends AppCompatActivity {

    private EditText category,amount,dueDate,paidAmt;
    private Button edit;
    private DBHandler dbHandler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        context = this;
        dbHandler = new DBHandler(context);

        category = findViewById(R.id.categoryUP);
        amount = findViewById(R.id.amountUP);
        dueDate = findViewById(R.id.descriptionUP);
        paidAmt = findViewById(R.id.paidAmt);
        edit = findViewById(R.id.buttonUpdate);

        //get single bill by id
        final String id = getIntent().getStringExtra("id");
        personalBill perrsonal_bill = dbHandler.getSingleBill(Integer.parseInt(id));

        //retrieve and set data in text feilds
        category.setText(perrsonal_bill.getCategory());
        amount.setText(String.valueOf((perrsonal_bill.getAmount())));
        dueDate.setText(perrsonal_bill.getDueDate());
        paidAmt.setText(String.valueOf(perrsonal_bill.getPaidAmount()));

        //when click on update button update data on the personaBill table
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //get updated values data
                String categoryText = category.getText().toString();
                double amountText = Double.parseDouble(amount.getText().toString());
                String dueDateText = dueDate.getText().toString();
                double PaidText = Double.parseDouble(paidAmt.getText().toString());

                    //calculate sub total
                    double subTotal = amountText - PaidText;
                    //view sub total in toast
                    Toast.makeText(getApplicationContext(), "Sub Total is :" + subTotal, Toast.LENGTH_LONG).show();

                personalBill personalbill = new personalBill(Integer.parseInt(id),categoryText,amountText,dueDateText,PaidText);
                //update table
                int state = dbHandler.updateSingleBill(personalbill);

                System.out.println(state);

                startActivity(new Intent(context,BillList.class));
            }
        });

    }
}