package com.techninja.sensei.weatherapp.Models;

import android.database.Observable;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dad on 2/3/2017.
 */

public class CityModel extends BaseObservable implements Serializable {
    private String _id;
    private String _name;

    @Bindable
    public String getId() {
        return this._id;
    }

    @Bindable
    public String getName() {
        return this._name;
    }

    @SerializedName("name")
    public void setName(String name) {
        this._name = name;
    }

    @SerializedName("_id")
    public void setId(String id) {
        this._id = id;
    }
}
