package com.example.alexbuicescu.smartlibraryandroid.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alexbuicescu on 10/23/16.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static Date stringToDate(String dateAsString){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateAsString);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            try {
                Date date = format.parse(dateAsString);
                return date;
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isDateSoon(String dateAsString) {
        if (dateAsString == null || dateAsString.equals("")) {
            return false;
        }

        Date theDate = stringToDate(dateAsString);
        if (theDate == null) {
            return false;
        }
        long diff = theDate.getTime() - new Date().getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        Log.i(TAG, "isDateSoon: " + days);

        return days < 67;
    }

    public static boolean isDateInPast(String dateAsString) {
        if (dateAsString == null || dateAsString.equals("")) {
            return false;
        }

        Date theDate = stringToDate(dateAsString);
        if (theDate == null) {
            return false;
        }
        long diff = theDate.getTime() - new Date().getTime();
        Log.i(TAG, "isDateInPast: " + diff);

        return diff < 0;
    }
}
