package com.avagar.sporty.room.converters;

import androidx.room.Ignore;
import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @Ignore
    public static String dateToString(Date date) {
        return new SimpleDateFormat("dd MMM yyyy").format(date);
    }

    @Ignore
    public static String longToString(Long value) {
        return new SimpleDateFormat("dd MMM yyyy").format(new Date(value));
    }
}
