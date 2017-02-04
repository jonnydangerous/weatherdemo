package com.techninja.sensei.weatherapp;

import com.techninja.sensei.weatherapp.Models.WeatherResponse;
import com.techninja.sensei.weatherapp.Persistence.IRepository;
import com.techninja.sensei.weatherapp.Presenters.MainPresenter;
import com.techninja.sensei.weatherapp.Services.IWeatherWrapper;
import com.techninja.sensei.weatherapp.Views.IMainView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Callback;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTests {
    MainPresenter _presenter;
    IMainView _view;
    IRepository _repos;
    private IWeatherWrapper _weather;

    List<WeatherResponse> _weatherResponse = mock(ArrayList.class);

    @Before
    public void SetUp() {
        _view = mock(IMainView.class);
        _repos = mock(IRepository.class);
        _weather = mock(IWeatherWrapper.class);

        _presenter = Mockito.spy(new MainPresenter(_repos, _view, _weather));

    }

    @Test
    public void UpdateView_SetCitiesAndSelectedCity() {
        //Arrange
        List<Integer> expectedCities = new ArrayList<>();
        expectedCities.add(1);
        expectedCities.add(2);
        when(_repos.GetCities()).thenReturn(expectedCities);
        when(_view.GetWeatherCallback(anyInt())).thenReturn(mock(Callback.class));

        //Act
        _presenter.UpdateView();

        //Assert
        verify(_weather, times(2)).GetWeatherData(anyInt(), Mockito.any(Callback.class));
    }

    @Test
    public void UpdateCityData_HappyPath() {
        //Arrange
        _presenter.WeatherData = _weatherResponse;
        _getIconFromWeatherStub();

        //Act
        _presenter.UpdateCityData();

        //Assert
        verify(_presenter).SetSelectedCity(anyInt());
        verify(_view).SetCities(_weatherResponse);
    }

    @Test
    public void SetSelectedCity_HappyPath() {
        //Arrange
        _presenter.WeatherData = _weatherResponse;
        when(_weather.GetIcon(anyString())).thenReturn(1);
        _getIconFromWeatherStub();

        //Act
        _presenter.SetSelectedCity(0);

        //Assert
        verify(_view).SetSelectedCity(_weatherResponse.get(0), 1);
    }

    @Test
    public void FindCity_HappyPath() {
        //Arrange
        _presenter.WeatherData = _weatherResponse;
        when(_weather.GetIcon(anyString())).thenReturn(1);
        _getIconFromWeatherStub();

        //Act
        _presenter.SetSelectedCity(0);

        //Assert
        verify(_view).SetSelectedCity(_weatherResponse.get(0), 1);
    }

    private void _getIconFromWeatherStub() {
        WeatherResponse.Weather mockWeather = mock(WeatherResponse.Weather.class);
        WeatherResponse mockResponse = new WeatherResponse(mock(WeatherResponse.class));
        mockResponse.setWeather(new ArrayList<>(Arrays.asList(mockWeather)));
        when(_weatherResponse.get(0)).thenReturn(mockResponse);
        when(mockWeather.getIcon()).thenReturn("1");
    }


}