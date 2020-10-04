package com.example.bills;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BillList extends AppCompatActivity {

    private Button add;
    private ListView listView;
    Context context;
    private DBHandler dbHandler;
    private List<personalBill> bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);

        add = findViewById(R.id.button2);
        listView = findViewById(R.id.pendingBillList);
        bills = new ArrayList<>();
        context = this;

        dbHandler = new DBHandler(context);

        //show all inserted bills
        bills = dbHandler.getAllBills();

        //call adapter to view billList
        personalBillAdapter adapter = new personalBillAdapter(context, R.layout.bill, bills);

        //call adapter by list view object
        listView.setAdapter(adapter);

        //when click on add Bill button direct to the add bill activity
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, addBill.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final personalBill pbill = bills.get(position);

                //create alert dialog builder and save each bill title and message
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(pbill.getCategory());
                builder.setMessage(pbill.getDueDate());

                //negative button for delete
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                dbHandler.deleteBill(pbill.getId());
                startActivity(new Intent(context,BillList.class));
                }
                });

                //neutral button for update and payment calculations
                //get the ID of bill and put data of the bill on update activity
                builder.setNeutralButton("Update & Mark Paid", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, detailsView.class);
                        intent.putExtra("id", String.valueOf(pbill.getId()));
                        startActivity(intent);
                    }
                });

                builder.show();
            }
        });
    }
}