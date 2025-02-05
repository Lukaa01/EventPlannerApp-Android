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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.model.Event1;

import java.util.ArrayList;

public class EventListAdapter1 extends ArrayAdapter<Event1> {
    private ArrayList<Event1> aEvents;
    public EventListAdapter1(Context context, ArrayList<Event1> events) {
        super(context, R.layout.event_card1, events);
        aEvents = events;
    }
    @Override
    public int getCount() {
        return aEvents.size();
    }
    @Nullable
    @Override
    public Event1 getItem(int position) {
        return aEvents.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event1 eventItem = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_card1,
                    parent, false);
        }
        LinearLayout eventCard = convertView.findViewById(R.id.event_card_item);
        TextView eventName = convertView.findViewById(R.id.event_name);
        TextView eventLocation = convertView.findViewById(R.id.event_location);
        TextView eventDate = convertView.findViewById(R.id.event_date);
        TextView eventNumberOfGuests = convertView.findViewById(R.id.event_number_of_guests);

        if(eventItem != null){
            eventName.setText(eventItem.getName());
            eventLocation.setText("Location: " + eventItem.getLocation());
            eventDate.setText("Date: " + eventItem.getDate());
            eventNumberOfGuests.setText("Guests: " + eventItem.getNumberOfGuests());

            eventCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + eventItem.getName() + ", id: " +
                        eventItem.getId());
                Toast.makeText(getContext(), "Clicked: " + eventItem.getName() +
                        ", id: " + eventItem.getId(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }

}
