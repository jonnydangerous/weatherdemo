package com.techninja.sensei.weatherapp;

import com.techninja.sensei.weatherapp.Persistence.IRepository;
import com.techninja.sensei.weatherapp.Persistence.ISharedPreferences;
import com.techninja.sensei.weatherapp.Persistence.Repository;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

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

    @Before
    public void SetUp() {
        _preferences = mock(ISharedPreferences.class);
        _stream  = mock(InputStream.class);

        _repos = Repository.getInstance(_preferences,_stream);
    }

    @Test
    public void GetCities_HappyPath() {
        //Arrange
        List<Integer> expectedCities = new ArrayList<>();
        expectedCities.add(1);
        expectedCities.add(2);
        when(_preferences.GetIntSet("cities")).thenReturn(expectedCities);

        //Act
        List<Integer> actualCities =_repos.GetCities();

        //Assert
        Assert.assertEquals(actualCities,expectedCities);
    }

    @Test
    public void AddCity_HappyPath() {
        //Arrange
        List<Integer> expectedCities = new ArrayList<>();
        expectedCities.add(1);
        expectedCities.add(2);

        when(_preferences.GetIntSet("cities")).thenReturn(expectedCities);

        //Act
        _repos.AddCity(3);

        //Assert
        verify(_preferences).GetIntSet(Mockito.eq("cities"));
        verify(_preferences).SetIntSet(Mockito.eq("cities"), Mockito.eq(expectedCities));

    }
}
