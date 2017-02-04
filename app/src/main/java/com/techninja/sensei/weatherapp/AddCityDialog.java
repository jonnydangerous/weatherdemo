package com.techninja.sensei.weatherapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.techninja.sensei.weatherapp.Utilities.FontHelper;
import com.techninja.sensei.weatherapp.Views.IAddCityDialogView;

public class AddCityDialog extends AppCompatDialogFragment implements IAddCityDialogView {
    private View _view;
    private TextWatcher _onChangeWatcher;
    //        private Function<Task, Boolean> dismissCallback;
//        private Task _task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.activity_add_city_dialog, container, false);
        FontHelper.markAsIconContainer(getContext(), _view);
        getDialog().setTitle("Lookup city");
        setupSaveButtonClick(_view);
        EditText cityEditText = (EditText) _view.findViewById(R.id.city_lookup);
        setUpOnChange(cityEditText);
        return _view;
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

    private void setUpOnChange(EditText editText) {
        editText.addTextChangedListener(_onChangeWatcher);

    }

    @Override
    public void SetChangeEvent(TextWatcher textWatcher) {
        _onChangeWatcher = textWatcher;
    }
}

