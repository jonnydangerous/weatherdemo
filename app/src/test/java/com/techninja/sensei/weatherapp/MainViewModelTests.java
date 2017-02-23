package com.techninja.sensei.weatherapp;

import com.techninja.sensei.weatherapp.Models.MainViewModel;
import com.techninja.sensei.weatherapp.Models.WeatherResponse;
import com.techninja.sensei.weatherapp.Persistence.IRepository;
import com.techninja.sensei.weatherapp.Services.IWeatherWrapper;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jonathan.brown on 2/22/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTests {

    private IRepository _repository;
    private IWeatherWrapper _weather;

    @Before
    public void SetUp(){
        _repository = mock(IRepository.class);
        _weather = mock(IWeatherWrapper.class);
    }

    @Test
    public void HappyPath(){
        //Arrange
        List<Integer> expectedIds = new ArrayList<>();
        expectedIds.add(1);
        expectedIds.add(2);
        final Response<WeatherResponse> mockResponse = mock(Response.class);
        WeatherResponse weatherResponse = new WeatherResponse(mock(WeatherResponse.class));
        weatherResponse.setName("CITY");
        when(mockResponse.body()).thenReturn(weatherResponse);
        when(_repository.GetCities()).thenReturn(expectedIds);
        when(_weather.GetWeatherData(anyInt(),any(Callback.class))).thenAnswer(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        ((Callback<WeatherResponse>) invocation.getArguments()[1]).onResponse(null, mockResponse);
                        return null;
                    }
                }
        );

        //Act
        MainViewModel model = new MainViewModel(_repository,_weather,null,null);

        //Assert
        verify(_weather, times(2)).GetWeatherData(anyInt(), Mockito.any(Callback.class));
        Assert.assertNotNull(model.SelectedWeather.get());
    }
}
