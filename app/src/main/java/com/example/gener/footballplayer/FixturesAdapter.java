package com.example.gener.footballplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gener on 26/03/2018.
 */

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.FixturesViewHolder>{


    private Context mCtx;
    private List<Fixtures> fixturesList;
    private ImageView iv_card_img;

    public FixturesAdapter(Context mCtx, List<Fixtures> fixturesList) {
        this.mCtx = mCtx;
        this.fixturesList = fixturesList;
    }

    @Override
    public FixturesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cell_cards, null);
        FixturesViewHolder holder = new FixturesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FixturesViewHolder holder, int position)
    {
        Fixtures match = fixturesList.get(position);
        holder.tv_home.setText(match.getHome_team());
        holder.tv_date.setText(match.getMatch_date());
        holder.tv_away.setText(match.getAway_team());

    }

    @Override
    public int getItemCount() {
        return fixturesList.size();
    }

    class FixturesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv_home;
        TextView tv_away;
        TextView tv_date;

        public FixturesViewHolder(View itemView) {
            super(itemView);
            ArrayList<Fixtures> fixtures = new ArrayList<>();
            itemView.setOnClickListener(this);
            tv_home = itemView.findViewById(R.id.tv_home);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_away = itemView.findViewById(R.id.tv_away);
            //iv_card_img = (ImageView) itemView.findViewById(R.id.iv_card_img);

        }

        @Override
        public void onClick(View v)
        {

            int position = getAdapterPosition();

            Fixtures fixtures = fixturesList.get(position);

            Intent intent = new Intent(mCtx, DetailsActivity.class);
            intent.putExtra("id",fixtures.getComp_id());
            intent.putExtra("localteam_name",fixtures.getHome_team());
            intent.putExtra("visitorteam_name",fixtures.getAway_team());
            intent.putExtra("week",fixtures.getWeek());
            intent.putExtra("venue",fixtures.getVenue());
            intent.putExtra("time",fixtures.getMatch_date());

            mCtx.startActivity(intent);

        }
    }
}
