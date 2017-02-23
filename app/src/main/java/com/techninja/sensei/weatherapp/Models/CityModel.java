package com.techninja.sensei.weatherapp.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jonathan.brown on 2/3/2017.
 */

public class CityModel extends BaseObservable implements Serializable {
    private int _id;
    private String _name;
    public String name;

    @Bindable
    public int getId() {
        return this._id;
    }

    @Bindable
    public String getName() {
        return this.name;
    }

    @SerializedName("name")
    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("_id")
    public void setId(int id) {
        this._id = id;
    }
}
