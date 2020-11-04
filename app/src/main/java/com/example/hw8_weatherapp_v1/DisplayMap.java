package com.example.hw8_weatherapp_v1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class DisplayMap extends AppCompatActivity {
    public static  String latitude = "com.example.hw8_weatherapp_v1.latitude";
    public static  String longitude = "com.example.hw8_weatherapp_v1.longitude";


    TextView city;
    TextView temp;
    TextView min;
    TextView max;
    TextView feels;
    TextView Lat;
    TextView Lon;
    Button map;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_map);
        //WeatherData w = City.getWeatherInstance();

    }



    public static double KtoF(Double k){
        return ((k - 273.15) * 9/5 + 32);
    }
}
