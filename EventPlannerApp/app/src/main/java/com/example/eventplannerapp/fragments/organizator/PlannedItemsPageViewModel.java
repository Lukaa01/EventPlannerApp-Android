package com.example.eventplannerapp.fragments.organizator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlannedItemsPageViewModel extends ViewModel {
    private final MutableLiveData<String> searchText;
    public PlannedItemsPageViewModel() {
        searchText = new MutableLiveData<>();
        searchText.setValue("This is search help");
    }
    public LiveData<String> getText() { return searchText; }
}
