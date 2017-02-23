package com.techninja.sensei.weatherapp.Models;

import android.content.res.Resources;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.techninja.sensei.weatherapp.Persistence.IRepository;
import com.techninja.sensei.weatherapp.Services.IWeatherWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel {
    public ObservableField<WeatherResponse> SelectedWeather = new ObservableField<>();
    public ObservableList<WeatherResponse> WeatherData = new ObservableArrayList<WeatherResponse>() {
    };
    private static Resources _resources;
    private static String _packageName;

    public static MainViewModel GetInstance() {
        return new MainViewModel(_resources, _packageName);
    }

    public MainViewModel(final Resources resources, final String packageName) {
        _resources = resources;
        _packageName = packageName;
    }

    public MainViewModel(IRepository repository, IWeatherWrapper weatherWrapper, final Resources resources, final String packageName) {
        _resources = resources;
        _packageName = packageName;
        final List<Integer> cities = repository.GetCities();
        int count = 0;
        for (final Integer city : cities) {
            final int loopCount = cities.indexOf(city);
            weatherWrapper.GetWeatherData(city, new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    WeatherData.add(response.body());
                    //       Log.i("WEATHER", response.body().getName());
                    if (loopCount + 1 == cities.size()) {
                        SetSelected();
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {

                }
            });
            count++;
        }
    }

    private void SetSelected() {
        if (SelectedWeather.get() == null) {
//            WeatherResponse foo = new WeatherResponse();
//            foo.getMain().get
            SelectedWeather.set(WeatherData.get(0));
        }
    }

    public void SetSelectedWeather(WeatherResponse weather) {
        SelectedWeather.set(weather);
    }

    public static Integer GetIcon(String iconId) {
        int icon;
        switch (iconId) {
            case "01d":
                icon = _resources.getIdentifier("@drawable/ic_weather_1", null, _packageName);
                break;
            case "01n":
                icon = _resources.getIdentifier("@drawable/ic_weather_3", null, _packageName);
                break;
            case "02d":
                icon = _resources.getIdentifier("@drawable/ic_weather_14", null, _packageName);
                break;
            case "02n":
                icon = _resources.getIdentifier("@drawable/ic_weather_9", null, _packageName);
                break;
            case "04d":
            case "04n":
                icon = _resources.getIdentifier("@drawable/ic_weather_25", null, _packageName);
                break;
            case "10d":
            case "10n":
            case "09n":
            case "09d":
                icon = _resources.getIdentifier("@drawable/ic_weather_18", null, _packageName);
                break;
            case "50d":
                icon = _resources.getIdentifier("@drawable/ic_weather_10", null, _packageName);
                break;
            case "50n":
                icon = _resources.getIdentifier("@drawable/ic_weather_11", null, _packageName);
                break;
            case "13d":
            case "13n":
                icon = _resources.getIdentifier("@drawable/ic_weather_23", null, _packageName);
                break;
            default:
                icon = _resources.getIdentifier("@drawable/ic_weather_2", null, _packageName);
                Log.i("ICON-UKNOWN: ", iconId);
                break;
        }
        return icon;
    }
}
