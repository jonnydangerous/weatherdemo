package com.techninja.sensei.weatherapp.Views;

import com.techninja.sensei.weatherapp.Models.WeatherResponse;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by jonathan.brown on 12/15/2016.
 */

public interface IMainView {
    void SetCities(List<WeatherResponse> cities);

    Callback<WeatherResponse> GetWeatherCallback(int currentCityPosition);

    void SetSelectedCity(WeatherResponse weatherResponse, int icon);

}
