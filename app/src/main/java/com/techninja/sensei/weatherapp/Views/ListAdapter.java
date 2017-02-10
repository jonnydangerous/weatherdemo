package com.techninja.sensei.weatherapp.Views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Dad on 2/7/2017.
 */

public class ListAdapter<T> extends BaseAdapter {
    private ObservableArrayList<T> list;
    private LayoutInflater inflater;

    public ListAdapter(ObservableArrayList<T> l) {
        list = l;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

//            ListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false);
//            binding.setInfo(list.get(position));

        return convertView;
    }
}

