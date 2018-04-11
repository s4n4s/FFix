package com.example.gener.footballplayer;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Getplayers extends IntentService {

    private String result1;

    public Getplayers() {
        super("Getplayers");
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {
        String dayDate = intent.getStringExtra("dayDate");
        try {
            OpenData opendata = new OpenData(dayDate);
            result1 = opendata.getData();

            writeToFile("fixtures.txt",result1);

            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.MATCH_UPDATE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile( String filename, String str) {
        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(str.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

