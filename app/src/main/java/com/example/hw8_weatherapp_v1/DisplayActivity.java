package com.example.hw8_weatherapp_v1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class DisplayActivity extends AppCompatActivity {
    public static final String latitude = "com.example.hw8_weatherapp_v1.latitude";
    public static final String longitude = "com.example.hw8_weatherapp_v1.longitude";

    TextView city;
    TextView temp;
    TextView min;
    TextView max;
    TextView feels;
    TextView Lat;
    TextView Lon;
    Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        WeatherData w = City.getWeatherInstance();
        city = findViewById(R.id.cityView);
        temp = findViewById(R.id.tempView);
        min = findViewById(R.id.minView);
        feels = findViewById(R.id.feelsView);
        max = findViewById(R.id.maxView);
        Lon = findViewById(R.id.Lon);
        Lat = findViewById(R.id.lat);
        map = findViewById(R.id.open_map1);


        city.setText("Weather for " + w.getCityName());
        temp.setText("Temperature : " + String.valueOf(Math.floor(KtoF(Double.parseDouble(w.getTemp())))) + "F");
        min.setText("Low : " + String.valueOf(Math.floor(KtoF(Double.parseDouble(w.getTempMin())))) + "F");
        max.setText("Maximum : " + String.valueOf(Math.floor(KtoF(Double.parseDouble(w.getTempMax())))) + "F");
        feels.setText("Feels like : " + String.valueOf(Math.floor(KtoF(Double.parseDouble(w.getFeelsLike())))) + "F");
      /*  map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });
    }
    public void openMap(){///when button is location city zip button clicked go to the City Activity
        Intent intent = new Intent(this, Maps.class);
        intent.putExtra("latitude", String.valueOf(Lat));
        intent.putExtra("longitude", String.valueOf(Lon));
        startActivity(intent);
    }*/

    }
    public static double KtoF(Double k){
        return ((k - 273.15) * 9/5 + 32);
    }
}
