package com.example.hw8_weatherapp_v1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechtoText extends Activity {
    public static final String EXTRA_TEXT = "com.example.hw8_weatherapp_v1.EXTRA_TEXT";
    private final int REQ_CODE = 100;
    TextView textView;
    public static WeatherData data = new WeatherData();
    private Context context;
    private FusedLocationProviderClient fusedLocationClient;
    public static final int LOCATION_REQUEST_CODE = 11;
    private RequestQueue queue;     public static final String DATA_TAG = "weather.data.go";


    //Declare UI elements


    //Volley request queue;
    //LOCATION REQUEST CODE KEY, not important since only asking for a single permission and don't need to distinguish


        //Remove that uggo TitleBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speechtotext);
        //getSupportActionBar().hide();

        //Set context
        context = getApplicationContext();

        queue = Volley.newRequestQueue(this);textView = findViewById(R.id.text);
        ImageView speak = findViewById(R.id.speak);
        context = getApplicationContext();
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList <String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    getWeather(result.get(0));
                    //getweather1(result.get(0));

                }
                break;
            }
        }
        }







    //implementing the OnSuccessListener in this activity since there is only a single callback.

        //Bundle key


        public static WeatherData getWeatherInstance() {
            return data;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void onLocClick(View v) {
            System.out.println("Location Button Clicked");
            //Ask for location permission
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                getLocation();
                System.out.println("PERMISSION ALREADY GRANTED FOR LOCATION, DOING ACTION");
            } else {
                //directly ask for the permission.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }



        public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                               int[] grantResults) {
            System.out.println("onRequestPermissionsResult Callback Entered");
            //Check that permission was granted
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }


        }

        @SuppressLint("MissingPermission")
        private void getLocation() {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, (OnSuccessListener<? super Location>) this);


        }

        //onSuccess for Location Services
        public void onSuccess(Location location) {
            //Get Weather by Location
            String lat = String.valueOf(location.getLatitude());
            String lon = String.valueOf(location.getLongitude());
            System.out.println("Lattitude = " + lat);
            System.out.println("Longitude = " + lon);
            data.setLat(lat);
            data.setLon(lon);
            getWeatherByLocation(lat, lon);

        }

        public void getWeather(String city) {

            String url = getString(R.string.WEATHER_API_URL_CITY) + city + getString(R.string.WEATHER_API_KEY);
            System.out.println(url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            JSONObject main = null;//display results
                            try {
                                main = response.getJSONObject("main");
                                JSONObject coords = response.getJSONObject("coord");
                                data.setLat(coords.getString("lat"));
                                data.setLon(coords.getString("lon"));
                                data.setTemp(main.getString("temp"));
                                data.setFeelsLike(main.getString("feels_like"));
                                data.setCityName(response.getString("name"));
                                data.setTempMax(main.getString("temp_max"));
                                data.setTempMin(main.getString("temp_min"));
                                //TODO : Bundle the weather object and send to next activity
                                //Current implementation is just using static member

                                Intent intent = new Intent(context, Display_Act.class);
                                startActivity(intent);


                            } catch (JSONException e) {
                                System.out.println("JSON EXPLOSION");
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("ERROR WITH VOLLEY REQUEST");

                        }
                    });

            queue.add(jsonObjectRequest);

        }



        public void getWeatherByLocation(String lat, String lon) {
            String url = getString(R.string.WEATHER_API_URL_LAT) + lat + getString(R.string.WEATHER_LON_SUFFIX) + lon
                    + getString(R.string.WEATHER_API_KEY);

            System.out.println(url);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            JSONObject main = null;
                            try {
                                main = response.getJSONObject("main");
                                data.setTemp(main.getString("temp"));
                                data.setFeelsLike(main.getString("feels_like"));
                                data.setCityName(response.getString("name"));
                                data.setTempMax(main.getString("temp_max"));
                                data.setTempMin(main.getString("temp_min"));
                                //TODO : Bundle the weather object and send to next activity
                                //Current implementation is just using static member

                                Intent intent = new Intent(context, DisplayActivity.class);
                                startActivity(intent);


                            } catch (JSONException e) {
                                System.out.println("JSON EXPLOSION");
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("ERROR WITH VOLLEY REQUEST");

                        }
                    });

            queue.add(jsonObjectRequest);
        }

    }