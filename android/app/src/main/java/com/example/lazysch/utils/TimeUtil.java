package com.example.lazysch.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime setTime(int hour, int minute) {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.of(hour,minute);
        java.time.LocalDateTime localDateTime = java.time.LocalDateTime.of(localDate,localTime);
        return localDateTime;
    }
}
