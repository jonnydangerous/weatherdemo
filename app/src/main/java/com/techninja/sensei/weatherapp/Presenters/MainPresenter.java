package com.techninja.sensei.weatherapp.Presenters;

import com.techninja.sensei.weatherapp.Models.CityModel;
import com.techninja.sensei.weatherapp.Models.WeatherResponse;
import com.techninja.sensei.weatherapp.Persistence.IRepository;
import com.techninja.sensei.weatherapp.Services.IWeatherWrapper;
import com.techninja.sensei.weatherapp.Views.IMainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan.brown on 12/15/2016.
 */

public class MainPresenter {
    private IMainView _view;
    private IWeatherWrapper _weatherWrapper;
    private IRepository _repository;

    public List<WeatherResponse> WeatherData;
    public int CityCount;

    public MainPresenter(IRepository repository, IMainView view, IWeatherWrapper weatherWrapper) {
        _repository = repository;
        _view = view;
        _weatherWrapper = weatherWrapper;
        WeatherData = new ArrayList<>();
    }

    public void UpdateView() {
        List<Integer> cities = _repository.GetCities();
        CityCount = cities.size();
        for (final Integer city : cities) {
            _weatherWrapper.GetWeatherData(city, _view.GetWeatherCallback(cities.indexOf(city) + 1));
        }
        _view.SetupDialog();
    }

    public void UpdateCityData() {
        SetSelectedCity(0);
        _view.SetCities(WeatherData);
    }

    public void SetSelectedCity(int position) {
        WeatherResponse weather = WeatherData.get(position);
        _view.SetSelectedCity(weather, _weatherWrapper.GetIcon(weather.getWeather().get(0).getIcon()));
    }

    public void LookupCity(String city) {
        List<CityModel> cities = _repository.LookupCity(city);
    }
}
