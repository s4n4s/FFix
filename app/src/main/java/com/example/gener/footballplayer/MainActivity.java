package com.example.gener.footballplayer;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView picked_date;
    ///public Button search_btn;
    public RecyclerView rv;
    public FixturesAdapter fa;
    public List<Fixtures> lf;
    public String dateForApi;
    public TextView tv_see_more;
    private ArrayList<String> list;

    public static final String MATCH_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    Calendar datetime = Calendar.getInstance();
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //***
        NotificationCompat.Builder notif = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentTitle("Bienvenue")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentText("Cette application est en cours de developpement");

        Notification builder = notif.build();
        NotificationManagerCompat.from(this).notify(1, builder);

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3B3B3B")));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        /**
         * Boutton recherche
         */
        /*search_btn = (Button) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/



        picked_date = (TextView) findViewById(R.id.picked_date);
        UpdateTextLabel();

        Intent intent = new Intent(MainActivity.this, Getplayers.class);
        intent.putExtra("dayDate", dateForApi);
        startService(intent);


        lf = new ArrayList<>();

        class FixturesUpdate extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,R.string.downloadText, Toast.LENGTH_SHORT).show();


                String contentFile = readFile("fixtures.txt");

                try
                {
                    JSONArray jsonarr = new JSONArray(contentFile);
                    int test = jsonarr.length();
                    if(lf.size() > 0)
                    {
                        lf.clear();
                    }
                    for(int i = 0;i < test;i++)
                    {
                        JSONObject jsonobj =  jsonarr.getJSONObject(i);
                        if(jsonobj.getString("time").equals(jsonobj.getString("status")) || jsonobj.getString("status").equals(""))
                        {
                            lf.add(new Fixtures(jsonobj.getString("localteam_name"),jsonobj.getString("visitorteam_name"),jsonobj.getString("formatted_date")+"\n"+jsonobj.getString("time")+" GMT +0", jsonobj.getString("comp_id"),jsonobj.getString("week"), jsonobj.getString("venue")));

                        }
                        else
                        {
                            lf.add(new Fixtures(jsonobj.getString("localteam_name"),jsonobj.getString("visitorteam_name"),jsonobj.getString("formatted_date")+"\n"+jsonobj.getString("status"), jsonobj.getString("comp_id"),jsonobj.getString("week"), jsonobj.getString("venue")));
                        }
                    }



                    fa.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //retour du service
        IntentFilter intentFilter = new IntentFilter(MATCH_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new FixturesUpdate(), intentFilter);

        //gestion du recycler view

        rv = (RecyclerView) findViewById(R.id.recycler_fixtures);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(this));


        fa = new FixturesAdapter(this, lf);
        rv.setAdapter(fa);


    }

    /**
     * Traitement bouttons
     */

    /**
     * Afficher le calendrier
     */
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            datetime.set(Calendar.YEAR, year);
            datetime.set(Calendar.MONTH, month);
            datetime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            UpdateTextLabel();
            Intent intent = new Intent(MainActivity.this, Getplayers.class);
            intent.putExtra("dayDate", dateForApi);
            startService(intent);
        }
    };

    /**
     * Mettre à jour le text label
     */
    private void UpdateTextLabel()
    {

        picked_date.setText(formatDateTime.format(datetime.getTime()));
        String monthNumber;
        String dayNumber;
        String yearNumber;


        int mn = datetime.get(Calendar.MONTH) + 1;
        int dn = datetime.get(Calendar.DAY_OF_MONTH);
        int yn = datetime.get(Calendar.YEAR);

        if(mn < 10)
        {
            monthNumber = "0" + mn;
            yearNumber = ""+yn;
        }
        else
        {
            monthNumber = ""+mn;
            yearNumber = ""+yn;
        }

        if(dn < 10)
        {
            dayNumber = "0" + dn;
            yearNumber = ""+yn;
        }
        else
        {
            dayNumber = ""+dn;
            yearNumber = ""+yn;
        }

        this.dateForApi = dayNumber+"."+monthNumber+"."+yearNumber;

    }

    @Override
    public void onClick(View view) {


    }

    /**
     * Ajouter un menu à l'activity en cours
     */

     @Override
    public boolean onCreateOptionsMenu(Menu menu)
     {
         getMenuInflater().inflate(R.menu.main_menu, menu);
         return true;
     }

    /**
     * Traiter les items du menu
     */

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.calendar_action_bar:
                new DatePickerDialog(this, d, datetime.get(Calendar.YEAR), datetime.get(Calendar.MONTH), datetime.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.get_faq:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://football-api.com"));
                startActivity(browserIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Ouvrir un fichier
     */

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


}
