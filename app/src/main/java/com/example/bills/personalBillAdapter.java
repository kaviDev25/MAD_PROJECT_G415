package com.example.bills;
//IT19218472
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class personalBillAdapter extends ArrayAdapter<personalBill> {

    private Context context;
    private int resource;
    List<personalBill> bills;

    public personalBillAdapter(@NonNull Context context, int resource, @NonNull List<personalBill> bills) {
        super(context, resource, bills);

        this.context = context;
        this.resource = resource;
        this.bills = bills;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
       View row = inflater.inflate(resource,parent,false);

        TextView category = row.findViewById(R.id.Billtitle);
        TextView description;
        description = row.findViewById(R.id.description);
        TextView paid = row.findViewById(R.id.paid);

        personalBill personalbill = bills.get(position);
        category.setText(personalbill.getCategory());
        description.setText(personalbill.getDueDate());
        paid.setVisibility(row.INVISIBLE);

        return row;
    }
}
