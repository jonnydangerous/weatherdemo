package com.techninja.sensei.weatherapp.Utilities;

/**
 * Created by jonathan.brown on 12/9/2016.
 */

public class ASCIIUtil {

    public static boolean isLetterOrNumber(int value) {
        return isLetter(value) || isNumber(value);
    }

    public static boolean isLetter(int value) {
        return isUpperCaseLetter(value) || isLowerCaseLetter(value);
    }

    public static boolean isUpperCaseLetter(int value) {
        return (value >= 65 && value <= 90); // A - Z
    }

    public static boolean isLowerCaseLetter(int value) {
        return (value >= 97 && value <= 122);  // a - z
    }

    public static boolean isNumber(int value) {
        return (value >= 48 && value <= 57); // 0 - 9
    }
}
