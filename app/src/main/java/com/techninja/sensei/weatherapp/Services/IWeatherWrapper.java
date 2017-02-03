package com.techninja.sensei.weatherapp.Services;


import com.techninja.sensei.weatherapp.Models.WeatherResponse;

import retrofit2.Callback;

/**
 * Created by jonathan.brown on 12/15/2016.
 */

public interface IWeatherWrapper {

    String GetWeatherData(int locationId, Callback<WeatherResponse> callback);

    Integer GetIcon(String iconId);
}
