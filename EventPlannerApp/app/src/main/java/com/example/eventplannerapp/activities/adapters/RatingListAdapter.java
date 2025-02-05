package com.example.eventplannerapp.activities.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Rating;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

import javax.annotation.Nullable;

public class RatingListAdapter extends ArrayAdapter<Rating> {

    private ArrayList<Rating> aRatings;
    private Consumer<Rating> mListener;

    public RatingListAdapter(Context context, ArrayList<Rating> ratings) {
        super(context, R.layout.rating_card, ratings);
        aRatings = ratings;
    }

    @Override
    public int getCount() { return aRatings.size(); }

    @Nullable
    @Override
    public Rating getItem(int position) { return aRatings.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @NotNull
    @Override
    public View getView(int position, @Nullable View convertView, @NotNull ViewGroup parent) {
        Rating rating = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rating_card, parent, false);
        }
        LinearLayout ratingCard = convertView.findViewById(R.id.rating_card_item);
        TextView ratingUsername = convertView.findViewById(R.id.rating_username);
        TextView ratingDate = convertView.findViewById(R.id.rating_date);
        TextView ratingComment = convertView.findViewById(R.id.rating_comment);
        TextView ratingRating = convertView.findViewById(R.id.rating_rating);

        if (rating != null) {
            ratingUsername.setText(rating.getUsername());
            ratingDate.setText(rating.getDate().toString());
            ratingComment.setText(rating.getComment());
            ratingRating.setText(String.valueOf(rating.getRating()));

            ratingCard.setOnClickListener(v -> {
                Log.i("ShopApp", "Clicked: Rating ID: " + rating.getId());
                Toast.makeText(getContext(), "Clicked rating ID: " + rating.getId() , Toast.LENGTH_SHORT).show();
                if (mListener != null) {
                    mListener.accept(rating);
                }
            });
        }
        return convertView;
    }

    public void setOnRatingSelectedListener(Consumer<Rating> listener) { mListener = listener; }
}
