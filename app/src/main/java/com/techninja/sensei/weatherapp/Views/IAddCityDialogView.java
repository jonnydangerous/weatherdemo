package com.techninja.sensei.weatherapp.Views;

import android.support.v4.app.FragmentManager;
import android.text.TextWatcher;

import com.techninja.sensei.weatherapp.Models.CityModel;

import java.util.List;

/**
 * Created by jonathan.brown on 1/27/2017.
 */

public interface IAddCityDialogView {
    void SetChangeEvent(TextWatcher textWatcher);
    void SetMatchedCities(List<CityModel> cities);
//    void AddCityClickEvent(Function<Integer,Integer> callback);

    void show(FragmentManager supportFragmentManager, String addCityDialog);
}
