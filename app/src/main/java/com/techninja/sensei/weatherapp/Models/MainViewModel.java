package com.techninja.sensei.weatherapp.Models;

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

    public MainViewModel(IRepository repository, IWeatherWrapper weatherWrapper) {
        final List<Integer> cities = repository.GetCities();
        int count = 0;
        for (final Integer city : cities) {
            final int loopCount = count;
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
            SelectedWeather.set(WeatherData.get(0));
        }
    }
}
