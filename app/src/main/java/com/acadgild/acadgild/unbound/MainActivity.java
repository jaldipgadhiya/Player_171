package com.acadgild.acadgild.unbound;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Play & Stop Buttons
        Button play,stop;
        play = (Button)findViewById(R.id.button1);
        //On click of Play Button
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Intent 0bject
                Intent myIntent = new Intent(MainActivity.this, MyService.class);
                //Start Service with intent object
                startService(myIntent);

            }
        });

        stop=(Button)findViewById(R.id.button2);
        //On click of Stop Button
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Intent 0bject
                Intent myIntent = new Intent(MainActivity.this, MyService.class);
                //Stop Service with intent object
                stopService(myIntent);
            }
        });

    }


}
