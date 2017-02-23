package com.techninja.sensei.weatherapp;

import com.google.gson.Gson;
import com.techninja.sensei.weatherapp.Models.CityModel;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Dad on 2/3/2017.
 */

public class JsonToCityModelTests {
    private String TEST_CITY = "{'_id':1,'name':'CITY',\"country\":\"USA\",\"coord\":{\"lon\":34.283333,\"lat\":44.549999}}";
    private String TEST_CITIES = "[{\"_id\":1,\"name\":\"CITY\",\"country\":\"USA\",\"coord\":{\"lon\":34.283333,\"lat\":44.549999}}," +
            "{\"_id\":2,\"name\":\"CITY2\",\"country\":\"USA\",\"coord\":{\"lon\":34.283333,\"lat\":44.549999}}]";

    @Test
    public void ParsesSingleCity() {
        CityModel testCity = new Gson().fromJson(TEST_CITY, CityModel.class);

        Assert.assertEquals(1, testCity.getId());
        Assert.assertEquals("CITY", testCity.getName());
//        Assert.assertEquals(testCity., "USA");
    }

    @Test
    public void ParsesListOfCities() {
        CityModel[] testCity = new Gson().fromJson(TEST_CITIES, CityModel[].class);

        Assert.assertEquals(1, testCity[0].getId());
        Assert.assertEquals("CITY", testCity[0].getName());
//        Assert.assertEquals(testCity[0].country, "USA");
        Assert.assertEquals(2, testCity[1].getId());
        Assert.assertEquals("CITY2", testCity[1].getName());
//        Assert.assertEquals(testCity[1].country, "USA");
    }
}
