package com.sharkit.nextmonday.ui.calculator;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_RATION_DATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.calculator.RationService;


public class Ration extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.calculator_ration, container, false);
        new RationService(requireArguments().getString(FRAGMENT_RATION_DATE))
                .findById(root)
                .writeToField()
                .activity()
                .setAdaptive();
        return root;
    }
}