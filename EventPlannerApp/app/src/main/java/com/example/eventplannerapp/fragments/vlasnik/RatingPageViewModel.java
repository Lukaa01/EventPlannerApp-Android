package com.example.eventplannerapp.fragments.vlasnik;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RatingPageViewModel extends ViewModel {

    private final MutableLiveData<String> searchText;

    public RatingPageViewModel() {
        searchText = new MutableLiveData<String>();
        searchText.setValue("This is search helpara.");
    }

    public MutableLiveData<String> getText() { return searchText; }
}
