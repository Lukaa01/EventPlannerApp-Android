package com.example.eventplannerapp.fragments.organizator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.R;

public class EventPageViewModel extends ViewModel {

    private final MutableLiveData<String> searchText;
    public EventPageViewModel(){
        searchText = new MutableLiveData<>();
        searchText.setValue("This is search help!");
    }
    public LiveData<String> getText(){
        return searchText;
    }

}