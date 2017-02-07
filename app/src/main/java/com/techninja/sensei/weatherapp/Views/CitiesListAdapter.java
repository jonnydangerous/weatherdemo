package com.techninja.sensei.weatherapp.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.techninja.sensei.weatherapp.Models.WeatherResponse;
import com.techninja.sensei.weatherapp.R;
import com.techninja.sensei.weatherapp.Services.IWeatherWrapper;

import java.util.List;


/**
 * Created by Dad on 1/10/2017.
 */

public class CitiesListAdapter extends BaseAdapter {
    private final Context _context;
//    private final List<WeatherResponse> _cities;
    private IWeatherWrapper _weather;
    private int _layout;
    private final LayoutInflater _inflater;
    public List<WeatherResponse> DataItems;

    public CitiesListAdapter(Context context, int layout,final List<WeatherResponse> cities, IWeatherWrapper weather) {
        _layout = layout;
        _context = context;
        _inflater = LayoutInflater.from(context);
        DataItems = cities;
        _weather = weather;
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
        final View convertView = view == null ? _inflater.inflate(_layout, parent, false) : view;
        final TextView tempView = (TextView) convertView.findViewById(R.id.temperature);
        tempView.setText(Math.round(city.getMain().getTemp()) + "\u00b0 F");
        final TextView taskName = (TextView) convertView.findViewById(R.id.city_name);
        taskName.setText(city.getName());

        ImageView image = (ImageView)convertView.findViewById(R.id.weather_icon);
        image.setImageResource(_weather.GetIcon(city.getWeather().get(0).getIcon()));

        return convertView;
    }

}
