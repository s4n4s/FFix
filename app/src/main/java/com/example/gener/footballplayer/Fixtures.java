package com.example.gener.footballplayer;

/**
 * Created by gener on 26/03/2018.
 */

public class Fixtures {

    private String comp_id;
    private String localteam_name;
    private String visitorteam_name;
    private String formatted_date;
    private String week;
    private String venue;





    public Fixtures(String home_team, String away_team, String match_date, String comp_id, String week, String venue) {
        this.localteam_name = home_team;
        this.visitorteam_name = away_team;
        this.formatted_date = match_date;
        this.comp_id = comp_id;
        this.week = week;
        this.venue = venue;

    }

    public String getComp_id() {
        return comp_id;
    }

    public String getHome_team() {
        return localteam_name;
    }

    public String getAway_team() {
        return visitorteam_name;
    }

    public String getMatch_date() {
        return formatted_date;
    }

    public String getWeek() {
        return week;
    }

    public String getVenue() {
        return venue;
    }

}
