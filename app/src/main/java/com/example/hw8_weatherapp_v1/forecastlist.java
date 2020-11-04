package com.example.hw8_weatherapp_v1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class forecastlist extends Activity {
    // Array of strings...
    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        simpleList = (ListView) findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, countryList);
        simpleList.setAdapter(arrayAdapter);
    }

    public void setLat(float lat) {
        //    this.lat = lat;
    }

    public void setLon(float lon) {
        //     this.lon = lon;
    }

    public void getDt() {
        // return dt;

    }
}