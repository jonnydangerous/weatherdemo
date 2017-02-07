package com.techninja.sensei.weatherapp.Persistence;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by jonathan.brown on 1/9/2017.
 */

public class SharedPreferencesWrapper implements ISharedPreferences {

    public static final int SAN_FRANCISCO_CA = 5391959;//"San Francisco, CA";
    public static final int NEW_YORK_NY = 5128638;// "New York, NY";
    public static final int SALT_LAKE_CITY_UT = 5780993;// "Salt Lake City, UT";
    private SharedPreferences _preferences;
    List<Integer> _cities = new ArrayList<>();

    public SharedPreferencesWrapper(SharedPreferences preferences) {
        _preferences = preferences;
        _cities.add(SAN_FRANCISCO_CA);
        _cities.add(NEW_YORK_NY);
        _cities.add(SALT_LAKE_CITY_UT);
    }

    @Override
    public List<Integer> GetIntSet(String key) {
        Set<String> set = _preferences.getStringSet(key, new HashSet<String>());
        for (String id : set) {
            _cities.add(Integer.parseInt(id));
        }
        return _cities;
    }

    @Override
    public void AddCity(String key, int cityId) {
        SharedPreferences.Editor editor = _preferences.edit();
        Set<String> ids = _preferences.getStringSet(key, new HashSet<String>());
        ids.add(Integer.toString(cityId));
        editor.putStringSet(key,ids);

        editor.apply();
    }

}
