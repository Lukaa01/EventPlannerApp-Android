package com.example.eventplannerapp.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationViewModel extends ViewModel {

    private final MutableLiveData<String> searchText;

    public NotificationViewModel() {
        searchText = new MutableLiveData<String>();
        searchText.setValue("This is search helpara.");
    }

    public MutableLiveData<String> getText() { return searchText; }
}
