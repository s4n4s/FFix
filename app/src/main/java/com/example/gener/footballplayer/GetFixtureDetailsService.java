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


public class GetFixtureDetailsService extends IntentService {

    private String result1;

    public GetFixtureDetailsService() {
        super("GetFixtureDetailsService");
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {
        String id = intent.getStringExtra("id");
        try {
            OpenDataGetDetails opendata = new OpenDataGetDetails(id);
            result1 = opendata.getData();

            writeToFile("details.txt",result1);



            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(DetailsActivity.DETAILS_UPDATE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile( String filename, String str) {
        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.flush();
            fos.write(str.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

