package com.techninja.sensei.weatherapp;

import com.techninja.sensei.weatherapp.Models.CityModel;
import com.techninja.sensei.weatherapp.Persistence.IRepository;
import com.techninja.sensei.weatherapp.Persistence.ISharedPreferences;
import com.techninja.sensei.weatherapp.Persistence.Repository;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jonathan.brown on 1/25/2017.
 */

public class RepositoryTests {
    ISharedPreferences _preferences;
    InputStream _stream;
    IRepository _repos;
    private String TEST_CITIES = "[{\"_id\":1,\"name\":\"CITY\",\"country\":\"USA\",\"coord\":{\"lon\":34.283333,\"lat\":44.549999}}," +
            "{\"_id\":2,\"name\":\"CITY2\",\"country\":\"USA\",\"coord\":{\"lon\":34.283333,\"lat\":44.549999}}]";
    @Before
    public void SetUp() {
        _preferences = mock(ISharedPreferences.class);
        _stream = new ByteArrayInputStream(TEST_CITIES.getBytes());
        _repos = Repository.getInstance(_preferences, _stream);
    }

    @Test
    public void GetCities_HappyPath() {
        //Arrange
        List<Integer> expectedCities = new ArrayList<>();
        expectedCities.add(1);
        expectedCities.add(2);
        when(_preferences.GetIntSet("cities")).thenReturn(expectedCities);

        //Act
        List<Integer> actualCities = _repos.GetCities();

        //Assert
        Assert.assertEquals(actualCities, expectedCities);
    }

    @Test
    public void AddCity_HappyPath() {
        //Act
        _repos.AddCity(3);

        //Assert
        verify(_preferences).AddCity(Mockito.eq("cities"), Mockito.eq(3));

    }

    @Test
    public void LookupCity_ReturnsBothMatches() {
        //Arrange
        List<Integer> expectedCities = new ArrayList<>();
        expectedCities.add(1);
        expectedCities.add(2);

        //Act
        List<CityModel> actual = _repos.LookupCity("CITY");

        //Assert
        Assert.assertEquals(actual.size(),2);
    }

    @Test
    public void LookupCity_ReturnsBothSingleMatch() {
        //Arrange
        List<Integer> expectedCities = new ArrayList<>();
        expectedCities.add(1);
        expectedCities.add(2);

        //Act
        List<CityModel> actual = _repos.LookupCity("CITY2");

        //Assert
        Assert.assertEquals(actual.size(),1);
        Assert.assertEquals(actual.get(0)._id,2);
    }
}
