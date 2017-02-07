package com.techninja.sensei.weatherapp.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.techninja.sensei.weatherapp.Models.CityModel;
import com.techninja.sensei.weatherapp.R;
import com.techninja.sensei.weatherapp.Utilities.FontHelper;

import java.util.List;

/**
 * Created by jonathan.brown on 2/3/2017.
 */

public class LookupCityAdapter extends ArrayAdapter {
    private final Context _context;
    private final List<CityModel> _cities;
    private int _layout;
    private final LayoutInflater _inflater;

    public LookupCityAdapter(Context context, int layout, List<CityModel> cities) {
        super(context, layout, cities);
        _layout = layout;
        _context = context;
        _inflater = LayoutInflater.from(context);
        _cities = cities;
    }

    public AdapterView.OnItemClickListener AddEvent= null;

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        final CityModel city = _cities.get(position);
        final View convertView = view == null ? _inflater.inflate(_layout, parent, false) : view;
        final TextView taskName = (TextView) convertView.findViewById(R.id.city_name);
        taskName.setText(city.name);
        final TextView add_icon = (TextView) convertView.findViewById(R.id.add_city_icon);
        FontHelper.markAsIconContainer(convertView.getContext(),add_icon);
//        add_icon.setOnClickListener(AddEvent);
        return convertView;
    }
}
