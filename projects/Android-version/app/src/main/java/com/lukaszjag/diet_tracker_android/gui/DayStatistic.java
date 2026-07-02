package com.lukaszjag.diet_tracker_android.gui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lukaszjag.diet_tracker_android.R;

public class DayStatistic extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_statistic);
    }
}
