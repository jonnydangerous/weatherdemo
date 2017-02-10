package com.techninja.sensei.weatherapp.Persistence;

import com.google.gson.Gson;
import com.techninja.sensei.weatherapp.Models.CityModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan.brown on 12/15/2016.
 */
public class Repository implements IRepository {
    private static Repository ourInstance;
    private static InputStream _fileStream;
    private static ISharedPreferences _preferences;
    private static CityModel[] _cities;

    public static Repository getInstance(ISharedPreferences preferences, InputStream fileStream) {
        ourInstance = new Repository(preferences);
        _fileStream = fileStream;
        _cities = new Gson().fromJson(loadJSONFromAsset(), CityModel[].class);
        return ourInstance;
    }

    private Repository(ISharedPreferences preferences) {

        _preferences = preferences;
    }

    private static final String TAG = "Repository";

    @Override
    public List<Integer> GetCities() {
        return _preferences.GetIntSet("cities");
    }

    @Override
    public void AddCity(int cityId) {
        _preferences.AddCity("cities", cityId);
    }

    @Override
    public List<CityModel> LookupCity(String city) {
        List<CityModel> matched = new ArrayList<>();
        for (CityModel cityModel : _cities) {
            if (cityModel.getName() != null && cityModel.getName().toLowerCase().startsWith(city.toLowerCase())) {
                matched.add(cityModel);
            }
        }
        return matched;
    }

    private static String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = _fileStream;
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
