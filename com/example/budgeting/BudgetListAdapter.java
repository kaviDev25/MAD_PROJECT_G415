package com.example.budgeting;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BudgetListAdapter extends RecyclerView.Adapter<BudgetListAdapter.BudgetsViewHolder>{
    //IT19097084 Ayodya Hettiarachchi

    //adapter class for budget name elements appearing on list
    private Context budgetsAdapterContext;
    private Cursor budgetsAdapterCursor;

    //budgetList Adapter Constructor
    public BudgetListAdapter(Context context, Cursor cursor){
        budgetsAdapterContext = context;
        budgetsAdapterCursor = cursor;
    }

    public class BudgetsViewHolder extends RecyclerView.ViewHolder{
        public TextView budgetName;

        public BudgetsViewHolder(@NonNull View itemView) {
            super(itemView);

            budgetName = itemView.findViewById(R.id.listBudgetName);
        }
    }

    @NonNull
    @Override
    public BudgetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(budgetsAdapterContext);
        View view = inflater.inflate(R.layout.activity_list_component, parent, false);
        return new BudgetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetsViewHolder holder, int position) {
        //ensures that the element displays data
        if(!budgetsAdapterCursor.moveToPosition(position)){
            return;
        }

        String name = budgetsAdapterCursor.getString(budgetsAdapterCursor.getColumnIndex(ShowBudgets.BudgetEntry.AVAILABLE_BUDGETS_COLUMN));

        holder.budgetName.setText(name);
    }

    @Override
    public int getItemCount() {
        return budgetsAdapterCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (budgetsAdapterCursor !=null){
            budgetsAdapterCursor.close();
        }

        budgetsAdapterCursor = newCursor;

        if (newCursor != null){
            notifyDataSetChanged();
        }
    }

}

