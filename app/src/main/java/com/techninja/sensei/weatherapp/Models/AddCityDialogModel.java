package com.techninja.sensei.weatherapp.Models;

import android.database.Observable;
import android.databinding.ObservableList;

/**
 * Created by Dad on 2/7/2017.
 */

public class AddCityDialogModel {
    public Observable<String> CityName;
    public ObservableList<CityModel> MatchedCities;

}
