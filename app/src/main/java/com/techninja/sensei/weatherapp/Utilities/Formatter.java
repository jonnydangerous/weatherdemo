package com.techninja.sensei.weatherapp.Utilities;

import com.techninja.sensei.weatherapp.Models.WeatherResponse;

/**
 * Created by jonathan.brown on 2/22/2017.
 */

public class Formatter {
    public static String FormatTemp(double value){
        return Math.round(value) + "\u00b0 F";
    }
    public static String FormatTemp(WeatherResponse value){
//        return Math.round(value) + "\u00b0 F";
    return "";
    }
}
