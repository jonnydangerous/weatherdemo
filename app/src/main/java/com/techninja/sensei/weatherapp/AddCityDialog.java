package com.techninja.sensei.weatherapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.techninja.sensei.weatherapp.Utilities.FontHelper;
import com.techninja.sensei.weatherapp.Views.IAddCityDialogView;

import java.util.function.Function;

public class AddCityDialog extends AppCompatDialogFragment implements IAddCityDialogView {
//        private Function<Task, Boolean> dismissCallback;
//        private Task _task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_city_dialog, container, false);
        FontHelper.markAsIconContainer(getContext(), view);
        getDialog().setTitle("Lookup city");

        setupSaveButtonClick(view);
        return view;
    }

    private void setupSaveButtonClick(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    if (dismissCallback.apply(_task)) {
//                        dismiss();
//                    }
            }
        });
    }

}

