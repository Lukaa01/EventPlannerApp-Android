package com.example.eventplannerapp.fragments.vlasnik;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PricelistPageViewModel extends ViewModel {

    private final MutableLiveData<String> searchText;
    public PricelistPageViewModel(){
        searchText = new MutableLiveData<>();
        searchText.setValue("Type name...");
    }

    public LiveData<String> getText(){
        return searchText;
    }
}
