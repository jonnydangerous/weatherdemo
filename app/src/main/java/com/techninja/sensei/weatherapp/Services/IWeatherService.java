package com.techninja.sensei.weatherapp.Services;

import com.techninja.sensei.weatherapp.Models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IWeatherService {
    @GET("weather")
    Call<WeatherResponse> GetWeatherByLocation(@Query("id") int location, @Query("appid") String apiKey, @Query("units")String units);
}
