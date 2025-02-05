package com.example.eventplannerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.fragments.CategoryFragment;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<String> categoryNames;
    private OnItemClickListener listener;

    public CategoryAdapter(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public interface OnItemClickListener {
        void onItemClick(String categoryName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String categoryName = categoryNames.get(position);
        holder.bind(categoryName);
    }

    @Override
    public int getItemCount() {
        return categoryNames.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private CategoryFragment categoryFragment;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryFragment = new CategoryFragment();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        String categoryName = categoryNames.get(position);
                        listener.onItemClick(categoryName);
                    }
                }
            });
        }

        public void bind(String categoryName) {
            categoryFragment.setCategoryName(categoryName);
        }
    }
}
