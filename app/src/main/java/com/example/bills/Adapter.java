package com.example.bills;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
//IT19753836

public class Adapter extends ArrayAdapter<ModelClass> {
    private Context context;
    private int resource;
    List<ModelClass> model;

    public Adapter(Context context, int resource, List<ModelClass> model) {
        super(context, resource, model);
        this.context = context;
        this.resource = resource;
        this.model = model;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        TextView type = row.findViewById(R.id.type);
        TextView Amount = row.findViewById(R.id.amount);
        ImageView imageView = row.findViewById(R.id.onGoing);

        ModelClass Model = model.get(position);
        type.setText(Model.getType());
        Amount.setText(Model.getAmount());
        imageView.setVisibility(row.INVISIBLE);

        if(Model.getFinished()>0){
            imageView.setVisibility(View.VISIBLE);
        }
        return row;
    }
}
