package com.kikulabs.moviecataloguelocalstorage.method;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateChange {
    public String changeFormatDate(String changeFormat) {
        try {
            @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            Date date = formatter.parse(changeFormat);
            return dateFormat.format(date);
        } catch (ParseException ignored) {
        }
        return changeFormat;
    }

    public static String getCurrentDates() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public String getLastYear(String setReleasedate) { //get year exp : (2018)
        String releaseYear;
        if (setReleasedate.length() >= 4) {
            releaseYear = setReleasedate.substring(setReleasedate.length() - 4);
        } else {
            releaseYear = "****";
        }
        return releaseYear;
    }
}
