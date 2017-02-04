package com.techninja.sensei.weatherapp;

import com.google.gson.Gson;
import com.techninja.sensei.weatherapp.Models.CityModel;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Dad on 2/3/2017.
 */

public class JsonToCityModelTests {
    private String TEST_CITY = "{\"_id\":1,\"name\":\"CITY\",\"country\":\"USA\",\"coord\":{\"lon\":34.283333,\"lat\":44.549999}}";
    private String TEST_CITIES = "[{\"_id\":1,\"name\":\"CITY\",\"country\":\"USA\",\"coord\":{\"lon\":34.283333,\"lat\":44.549999}}," +
            "{\"_id\":2,\"name\":\"CITY2\",\"country\":\"USA\",\"coord\":{\"lon\":34.283333,\"lat\":44.549999}}]";

    @Test
    public void ParsesSingleCity() {
        CityModel testCity = new Gson().fromJson(TEST_CITY, CityModel.class);

        Assert.assertEquals(testCity._id, 1);
        Assert.assertEquals(testCity.name, "CITY");
        Assert.assertEquals(testCity.country, "USA");
    }

    @Test
    public void ParsesListOfCities() {
        CityModel[] testCity = new Gson().fromJson(TEST_CITIES, CityModel[].class);

        Assert.assertEquals(testCity[0]._id, 1);
        Assert.assertEquals(testCity[0].name, "CITY");
        Assert.assertEquals(testCity[0].country, "USA");
        Assert.assertEquals(testCity[1]._id, 2);
        Assert.assertEquals(testCity[1].name, "CITY2");
        Assert.assertEquals(testCity[1].country, "USA");
    }
}
