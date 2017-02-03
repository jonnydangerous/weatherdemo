package com.techninja.sensei.weatherapp.Persistence;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jonathan.brown on 12/15/2016.
 */
public class Repository implements IRepository {
    private static Repository ourInstance;
    private static InputStream _fileStream;
    private static ISharedPreferences _preferences;

    public static Repository getInstance(ISharedPreferences preferences, InputStream fileStream) {
        ourInstance = new Repository(preferences);
        _fileStream = fileStream;
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
        List<Integer> cities = this.GetCities();
        cities.add(cityId);
        _preferences.SetIntSet("cities", cities);
    }

    public int LookupCity(String city) {
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo_inside = jsonArray.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("formule"));
                String formula_value = jo_inside.getString("formule");
                String url_value = jo_inside.getString("url");

                m_li = new HashMap<String, String>();
                m_li.put("formule", formula_value);
                m_li.put("url", url_value);

                formList.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String loadJSONFromAsset() {
        String json = null;
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
