package com.techninja.sensei.weatherapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.techninja.sensei.weatherapp.Models.CityModel;
import com.techninja.sensei.weatherapp.Utilities.FontHelper;
import com.techninja.sensei.weatherapp.Views.IAddCityDialogView;
import com.techninja.sensei.weatherapp.Views.LookupCityAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AddCityDialog extends AppCompatDialogFragment implements IAddCityDialogView {
    private View _view;
    private TextWatcher _onChangeWatcher;
    private List<CityModel> _cities;
    private LookupCityAdapter _adapter;
    public  Function<Integer,Integer> AddCityClickEvent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _cities = new ArrayList<>();
        _view = inflater.inflate(R.layout.activity_add_city_dialog, container, false);
        FontHelper.markAsIconContainer(getContext(), _view);
        getDialog().setTitle("Lookup city");
        EditText cityEditText = (EditText) _view.findViewById(R.id.city_lookup);
        setUpOnChange(cityEditText);
        return _view;
    }

    private void setUpOnChange(EditText editText) {
        editText.addTextChangedListener(_onChangeWatcher);
    }

    @Override
    public void SetChangeEvent(TextWatcher textWatcher) {
        _onChangeWatcher = textWatcher;
    }

    @Override
    public void SetMatchedCities(List<CityModel> cities) {
        _cities = cities;
        _adapter = new LookupCityAdapter(getContext(), R.layout.lookup_city_item, cities);
        ListView list = (ListView) _view.findViewById(R.id.matched_cities_list);
        list.setAdapter(_adapter);
        list.setOnItemClickListener(new ListView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Integer cityId = _cities.get(i)._id;
                AddCityClickEvent.apply(cityId);
                dismiss();
            }
        });
    }

//    @Override
//    public void AddCityClickEvent(Function<Integer, Integer> callback) {
//
//    }

//    @Override
//    public void AddCityClickEvent(long cityId) {
//
//    }
}

