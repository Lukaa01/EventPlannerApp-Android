package com.example.eventplannerapp.fragments.vlasnik;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServicePricelistViewModel extends ViewModel {
    private final MutableLiveData<String> searchText;
    public ServicePricelistViewModel(){
        searchText = new MutableLiveData<>();
        searchText.setValue("Type name...");
    }
    public LiveData<String> getText(){
        return searchText;
    }
}