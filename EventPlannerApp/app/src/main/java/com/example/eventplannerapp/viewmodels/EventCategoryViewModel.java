package com.example.eventplannerapp.viewmodels;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class EventCategoryViewModel extends ViewModel {
    private List<String> selectedEvents = new ArrayList<>();

    public List<String> getSelectedEvents() {
        return selectedEvents;
    }

    public void setSelectedEvents(List<String> selectedEvents) {
        this.selectedEvents = selectedEvents;
    }

    public void addEvent(String eventName) {
        if (!selectedEvents.contains(eventName)) {
            selectedEvents.add(eventName);
        }
    }

    public void removeEvent(String eventName) {
        selectedEvents.remove(eventName);
    }

    public void removeAll(){
        selectedEvents.clear();
    }
}
