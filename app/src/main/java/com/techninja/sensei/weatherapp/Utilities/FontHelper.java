package com.techninja.sensei.weatherapp.Utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by jonathan.brown on 12/8/2016.
 */

public class FontHelper {
    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public static void markAsIconContainer(View view, Typeface typeface) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                markAsIconContainer(child, typeface);
            }
        } else if (view instanceof TextView && !(view instanceof EditText)) {
            if ((((TextView) view).getText().toString() != null ||((TextView) view).getText().toString() !="") &&!ASCIIUtil.isLetterOrNumber(((TextView) view).getText().charAt(0))) {
                ((TextView) view).setTypeface(typeface);
            }
        }
    }

    public static void markAsIconContainer(Context context,View view) {
        Typeface font = FontHelper.getTypeface(context, FontHelper.FONTAWESOME);
        markAsIconContainer(view, font);
    }
}

