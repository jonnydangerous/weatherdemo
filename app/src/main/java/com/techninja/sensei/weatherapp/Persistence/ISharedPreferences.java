package com.techninja.sensei.weatherapp.Persistence;

import java.util.List;

/**
 * Created by jonathan.brown on 1/9/2017.
 */

public interface ISharedPreferences {
    List<Integer> GetIntSet(String cities);
    void SetIntSet(String key, List<Integer> list);
}
