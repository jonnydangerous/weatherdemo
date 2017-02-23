package com.techninja.sensei.weatherapp.Services;

import com.techninja.sensei.weatherapp.Models.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jonathan.brown on 12/15/2016.
 */

public class OpenWeatherWrapper implements IWeatherWrapper {

    private String _apiKey;
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String UNITS = "imperial";
    private IWeatherService _service;

    public OpenWeatherWrapper(String apiKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this._apiKey = apiKey;
        _service = retrofit.create(IWeatherService.class);

    }

    @Override
    public String GetWeatherData(int locationId, Callback<WeatherResponse> callback) {
        try {
            Call<WeatherResponse> weather = _service.GetWeatherByLocation(locationId, _apiKey, UNITS);
            weather.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

