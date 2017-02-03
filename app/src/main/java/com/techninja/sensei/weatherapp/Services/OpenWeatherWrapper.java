package com.techninja.sensei.weatherapp.Services;

import android.content.res.Resources;
import android.util.Log;

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
    IWeatherService _service;
    private Resources _resources;
    private String _packageName;


    public OpenWeatherWrapper(String apiKey, Resources resources, String packageName) {
        _resources = resources;
        _packageName = packageName;
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

    @Override
    public Integer GetIcon(String iconId){
        int icon =0;
        switch (iconId){
            case "01d": icon =  _resources.getIdentifier("@drawable/ic_weather_1", null,_packageName);
                break;
            case "02d": icon =  _resources.getIdentifier("@drawable/ic_weather_14", null,_packageName);
                break;
            case "04d": icon =  _resources.getIdentifier("@drawable/ic_weather_8", null,_packageName);
                break;
            case "04n": icon =  _resources.getIdentifier("@drawable/ic_weather_9", null,_packageName);
                break;
            case "50d": icon =  _resources.getIdentifier("@drawable/ic_weather_10", null,_packageName);
                break;
            case "50n": icon =  _resources.getIdentifier("@drawable/ic_weather_11", null,_packageName);
                break;
            case "13d": icon =  _resources.getIdentifier("@drawable/ic_weather_23", null,_packageName);
                break;
            case "13n": icon =  _resources.getIdentifier("@drawable/ic_weather_23", null,_packageName);
                break;
            default: icon = _resources.getIdentifier("@drawable/ic_weather_2", null,_packageName);
                Log.i("ICON-UKNOWN: ",iconId);
                break;
        }
        return  icon;
    }
}

