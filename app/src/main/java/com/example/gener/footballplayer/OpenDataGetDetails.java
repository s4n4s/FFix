package com.example.gener.footballplayer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by gener on 26/03/2018.
 */

public class OpenDataGetDetails {

    private static String dayDate = "";
    private static String URL ="";

    public OpenDataGetDetails(String comp_id)
    {
        this.URL = "http://api.football-api.com/2.0/competitions/"+comp_id+"?Authorization=565ec012251f932ea4000001cb8ded34c66049357de00daa0cd038ca";
    }


    public static String getData() throws Exception
    {
        String response = OkHttpUtils.sendGetOkHttpRequest(URL);
        return response;
    }
}
