package com.vvv.bball;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<Integer> {

    public LeaderboardAdapter(Context context, List<Integer> scores) {
        super(context, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_item, parent, false);
        }

        Integer currentScore = getItem(position);

        TextView rankTextView = convertView.findViewById(R.id.rankTextView);
        TextView scoreTextView = convertView.findViewById(R.id.scoreTextView);

        rankTextView.setText(String.format("%s:", position + 1));
        scoreTextView.setText(String.valueOf(currentScore));

        return convertView;
    }
}

