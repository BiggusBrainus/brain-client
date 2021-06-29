package at.htlkaindorf.bigbrain.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.beans.Rank;
import at.htlkaindorf.bigbrain.holder.RankingHolder;
import at.htlkaindorf.bigbrain.beans.User;

/**
 * Adapter for the RecyclerView in the RankingActivity
 * @version BigBrain v1
 * @since 11.06.2021
 * @author Nico Pessnegger
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingHolder> {
    private List<Rank> rankList = new ArrayList<>();
    private String unit;
    private User user;

    public RankingAdapter(List<Rank> rankList, String unit, User user) {
        this.rankList = rankList;
        this.unit = unit;
        this.user = user;
    }

    @NonNull
    @Override
    public RankingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
        TextView username = view.findViewById(R.id.tvUserName);
        TextView score = view.findViewById(R.id.tvScore);
        ConstraintLayout cl = view.findViewById(R.id.clRankingItem);

        RankingHolder holder = new RankingHolder(view, username, score, cl);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankingHolder holder, int position) {
        Rank rank = rankList.get(position);

        holder.getUsername().setText(rank.getUser().getUsername().toString());
        holder.getScore().setText(rank.getScore().toString() + " " + unit);
        if(this.user.getUid() == rank.getUser().getUid()){
            holder.getCl().setBackgroundColor(Color.rgb(81, 216, 216));
        }

    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }
}
