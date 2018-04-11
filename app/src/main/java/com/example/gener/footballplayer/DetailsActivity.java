package com.example.gener.footballplayer;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by gener on 09/04/2018.
 */


public class DetailsActivity extends AppCompatActivity {

    private TextView ad_tv_home;
    private TextView ad_tv_away;
    private TextView ad_tv_country;
    private TextView ad_tv_league;
    private TextView ad_tv_stage;
    private TextView ad_tv_stade;
    private TextView ad_tv_date;

    public static final String DETAILS_UPDATE = "com.octip.cours.inf4042_11.DETAILS_UPDATE";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3B3B3B")));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id_comp = getIntent().getStringExtra("id");

        ad_tv_home = (TextView) findViewById(R.id.ad_tv_home);
        ad_tv_away = (TextView) findViewById(R.id.ad_tv_away);
        ad_tv_date = (TextView) findViewById(R.id.ad_tv_date);
        ad_tv_country = (TextView) findViewById(R.id.ad_tv_country);
        ad_tv_league = (TextView) findViewById(R.id.ad_tv_league);
        ad_tv_stage = (TextView) findViewById(R.id.ad_tv_stage);
        ad_tv_stade = (TextView) findViewById(R.id.ad_tv_stade);


        ad_tv_home.setText(getIntent().getStringExtra("localteam_name"));
        ad_tv_away.setText(getIntent().getStringExtra("visitorteam_name"));
        ad_tv_stade.setText(getIntent().getStringExtra("venue"));
        ad_tv_stage.setText(getIntent().getStringExtra("week"));
        ad_tv_date.setText(getIntent().getStringExtra("time"));

        Intent intent = new Intent(DetailsActivity.this, GetFixtureDetailsService.class);
        intent.putExtra("id", id_comp);
        startService(intent);


        class Details_update extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                String contentFile = readFile("details.txt");


                try {
                    JSONObject jsonObject = new JSONObject(contentFile);
                   // JSONArray jsonArray = jsonObject.getJSONArray("name");
                    Log.d("leg",jsonObject.toString());
                    ad_tv_country.setText(jsonObject.getString("region"));
                    ad_tv_league.setText(jsonObject.getString("name"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        IntentFilter intentFilter = new IntentFilter(DETAILS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new Details_update(), intentFilter);
    }

    public String readFile(String file)
    {
        String text = "";

        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
