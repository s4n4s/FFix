package com.example.gener.footballplayer;

        import android.util.Log;

        import java.util.ArrayList;
        import java.util.List;

        import okhttp3.OkHttpClient;

/**
 * Created by gener on 26/03/2018.
 */

public class OpenData {

    private static String dayDate = "";
    private static String URL ="";

    public OpenData(String dayDate)
    {
        this.dayDate = dayDate;
        this.URL = "http://api.football-api.com/2.0/matches?match_date="+dayDate+"&Authorization=565ec012251f932ea4000001cb8ded34c66049357de00daa0cd038ca";
    }


    public static String getData() throws Exception
    {
        String response = OkHttpUtils.sendGetOkHttpRequest(URL);
        return response;
    }
}
