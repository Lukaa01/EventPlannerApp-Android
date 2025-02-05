package com.example.eventplannerapp.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.fragments.admin.OwnerDetailedFragment;
import com.example.eventplannerapp.model.Owner;

import java.util.List;

public class OwnerAdapter extends BaseAdapter {

    private List<Owner> owners;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;

    public OwnerAdapter(Context context, List<Owner> owners) {
        this.owners = owners;
        this.fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return owners.size();
    }

    @Override
    public Object getItem(int position) {
        return owners.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_owner, parent, false);
            holder = new ViewHolder();
            holder.ownerEmail = convertView.findViewById(R.id.owner_email);
            holder.companyEmail = convertView.findViewById(R.id.company_email);
            holder.companyName = convertView.findViewById(R.id.company_name);
            holder.ownerType = convertView.findViewById(R.id.owner_type);
            holder.ownerName = convertView.findViewById(R.id.owner_name);
            holder.ownerLastname = convertView.findViewById(R.id.owner_lastname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Owner owner = owners.get(position);
        holder.ownerEmail.setText("Owner Email:    " + owner.getEmail());
        holder.companyEmail.setText("Company Email:    " + owner.getCompanyEmail());
        holder.companyName.setText("Company Name:    " + owner.getCompanyName());
        holder.ownerType.setText("Company Type:    " + owner.getType().name());
        holder.ownerName.setText("Owner Name:    " + owner.getFirstname());
        holder.ownerLastname.setText("Owner Lastname:    " + owner.getLastname());

        convertView.setOnClickListener(v -> {
            OwnerDetailedFragment dialog = OwnerDetailedFragment.newInstance(owner.getId());
            dialog.show(fragmentManager, "OwnerDetailedDialogFragment");
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView ownerEmail;
        TextView companyEmail;
        TextView companyName;
        TextView ownerType;
        TextView ownerName;
        TextView ownerLastname;
    }

    public void filterList(List<Owner> filteredList) {
        this.owners.clear();
        this.owners.addAll(filteredList);
        notifyDataSetChanged();
    }
}
