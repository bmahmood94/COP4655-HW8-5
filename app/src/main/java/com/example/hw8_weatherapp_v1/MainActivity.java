package com.example.hw8_weatherapp_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.appbar_scrolling_view_behavior, R.string.appbar_scrolling_view_behavior);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.speech://for speech reco
                        TexttoSpeech();
                        break;
                    case R.id.location://for location zip city
                        getCity();
                        break;
                    case R.id.s2t://text to speech
                        SpeechtoText();
                        break;
                    default:
                        return true;
                }


                return true;

            }
        });


    }
    public void getCity(){///when button is location city zip button clicked go to the City Activity
        Intent intent = new Intent(this, City.class);

        startActivity(intent);
    }
    public void TexttoSpeech(){//calls text to speech when button clicked.
        Intent intent= new Intent(this,TexttoSpeech.class);
        startActivity(intent);
    }
    public void SpeechtoText(){//creates Activity to send to Speech to text
        Intent intent= new Intent(this, SpeechtoText.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}