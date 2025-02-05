package com.example.eventplannerapp.fragments.vlasnik;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReportPageViewModel extends ViewModel {

    private final MutableLiveData<String> searchText;
    public ReportPageViewModel(){
        searchText = new MutableLiveData<>();
        searchText.setValue("This is search help!");
    }
    public LiveData<String> getText(){
        return searchText;
    }

}
