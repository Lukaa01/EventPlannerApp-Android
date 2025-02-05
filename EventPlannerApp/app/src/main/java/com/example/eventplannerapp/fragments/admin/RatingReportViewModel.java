package com.example.eventplannerapp.fragments.admin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RatingReportViewModel extends ViewModel {
    private final MutableLiveData<String> searchText;

    public RatingReportViewModel() {
        searchText = new MutableLiveData<String>();
        searchText.setValue("This is search helpara.");
    }

    public MutableLiveData<String> getText() { return searchText; }

}
