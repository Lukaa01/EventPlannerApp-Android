package com.example.eventplannerapp.activities.adapters;

import com.example.eventplannerapp.EventType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eventplannerapp.EventType;

public class EventTypeAdapter extends ArrayAdapter<EventType> {

    private LayoutInflater inflater;

    public EventTypeAdapter(Context context, EventType[] eventTypes) {
        super(context, android.R.layout.simple_spinner_item, eventTypes);
        inflater = LayoutInflater.from(context);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        EventType eventType = getItem(position);
        if (eventType != null) {
            textView.setText(eventType.name().replace("_", " "));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        EventType eventType = getItem(position);
        if (eventType != null) {
            textView.setText(eventType.name().replace("_", " "));
        }

        return convertView;
    }
}
