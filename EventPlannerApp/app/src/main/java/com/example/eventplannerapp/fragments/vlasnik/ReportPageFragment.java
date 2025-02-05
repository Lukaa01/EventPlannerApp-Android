package com.example.eventplannerapp.fragments.vlasnik;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.CloudStoreUtil;
import com.example.eventplannerapp.FragmentTransition;
import com.example.eventplannerapp.R;
import com.example.eventplannerapp.ReportStatus;
import com.example.eventplannerapp.databinding.FragmentReportPageBinding;
import com.example.eventplannerapp.fragments.ReportFragment;
import com.example.eventplannerapp.model.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportPageFragment extends Fragment {

    public static ArrayList<Report> reports = new ArrayList<>();
    private FragmentReportPageBinding binding;
    private ReportPageViewModel reportsViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReportPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        reportsViewModel = new ViewModelProvider(this).get(ReportPageViewModel.class);

        reports = new ArrayList<>();
        prepareReportList(reports);

        return root;
    }

    public void prepareReportList(ArrayList<Report> reports){
        CloudStoreUtil.selectAllReports().thenAccept(reportList ->{
            reports.addAll(reportList);
            FragmentTransition.to(ReportFragment.newInstance(reports), getActivity(), false, R.id.scroll_report_list);
        });
    }
}