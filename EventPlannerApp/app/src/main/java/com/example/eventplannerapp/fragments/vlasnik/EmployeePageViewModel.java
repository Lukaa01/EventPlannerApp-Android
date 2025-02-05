package com.example.eventplannerapp.fragments.vlasnik;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventplannerapp.adapters.EmployeeAdapter;

public class EmployeePageViewModel extends ViewModel {

    private final MutableLiveData<String> searchText;

    public EmployeePageViewModel() {
        searchText = new MutableLiveData<>();
        searchText.setValue("Ovo je search helpara.");
    }

    public LiveData<String> getText() {
        return searchText;
    }

}
