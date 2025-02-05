package com.example.eventplannerapp.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.BudgetItem;
import com.example.eventplannerapp.model.Event1;

import java.util.ArrayList;

public class PlannedItemsListAdapter extends ArrayAdapter<BudgetItem> {
    private ArrayList<BudgetItem> aItems;
    public PlannedItemsListAdapter(Context context, ArrayList<BudgetItem> items) {
        super(context, R.layout.budget_item_card, items);
        aItems = items;
    }
    @Override
    public int getCount() {
        return aItems.size();
    }
    @Nullable
    @Override
    public BudgetItem getItem(int position) {
        return aItems.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BudgetItem item = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.budget_item_card, parent, false);
        }
        TextView itemSubcategory = convertView.findViewById(R.id.textViewSubcategory);
        TextView itemPrice = convertView.findViewById(R.id.textViewPrice);
        Button itemEdit = convertView.findViewById(R.id.buttonEditItem);
        Button itemDelete = convertView.findViewById(R.id.buttonDeleteItem);

        if(item != null) {
            itemSubcategory.setText(item.getSubcategory());
            itemPrice.setText("Price" + item.getPrice());


        }
        return convertView;
    }
}
