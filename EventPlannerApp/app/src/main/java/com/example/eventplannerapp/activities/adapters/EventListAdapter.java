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
import com.example.eventplannerapp.model.Employee;
import com.example.eventplannerapp.model.Event;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> aEvents;

    public EventListAdapter(Context context, ArrayList<Event> employees) {
        super(context, R.layout.employee_card, employees);
        aEvents = employees;
    }

    @Override
    public int getCount() {
        return aEvents.size();
    }

    @Nullable
    @Override
    public Event getItem(int position) {
        return aEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Event event = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_card,
                    parent, false);
        }
        LinearLayout eventCard = convertView.findViewById(R.id.event_card_item);
        TextView eventName = convertView.findViewById(R.id.event_name);
        TextView eventDay = convertView.findViewById(R.id.event_day);
        TextView eventHours = convertView.findViewById(R.id.event_hours);
        TextView eventStatus = convertView.findViewById(R.id.event_status);

        if(event != null){
            eventName.setText(event.getName());
            eventDay.setText(event.getDate().toString());
            eventHours.setText(String.format("%s - %s", event.getStartTime().toString(), event.getEndTime().toString()));
            eventStatus.setText(event.isBusy() ? "Busy" : "Reserved");

//            eventCard.setOnClickListener(v -> {
//                // Handle click on the item at 'position'
//                Log.i("ShopApp", "Clicked: " + event.getLastName() + ", id: " +
//                        event.getId().toString());
//                Toast.makeText(getContext(), "Clicked: " + event.getLastName()  +
//                        ", id: " + event.getId().toString(), Toast.LENGTH_SHORT).show();
//            });
        }

        return convertView;

    }

}
