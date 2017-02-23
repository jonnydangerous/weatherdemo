package com.techninja.sensei.weatherapp.Views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.techninja.sensei.weatherapp.Models.WeatherResponse;
import com.techninja.sensei.weatherapp.R;
import com.techninja.sensei.weatherapp.databinding.CityItemBinding;

import java.util.List;


/**
 * Created by jonathan.brown on 1/10/2017.
 */

public class CitiesListAdapter extends BaseAdapter {
    public List<WeatherResponse> DataItems;
    public CitiesListAdapter(final ObservableList<WeatherResponse> cities) {
        DataItems = cities;
    }

    @Override
    public int getCount() {
        return DataItems.size();
    }

    @Override
    public Object getItem(int i) {
        return DataItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return DataItems.get(i).getId();
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        WeatherResponse city = DataItems.get(position);
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        CityItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.city_item, parent, false);
        binding.setCity(city);
        return binding.getRoot();
    }
}
