package com.techninja.sensei.weatherapp.Persistence;

import com.techninja.sensei.weatherapp.Models.CityModel;

import java.util.List;

/**
 * Created by jonathan.brown on 12/15/2016.
 */

public interface IRepository {
    List<Integer> GetCities();
    void AddCity(int cityId);

    List<CityModel> LookupCity(String city);
}
