package at.htlkaindorf.bigbrain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.beans.LobbyHolder;
import at.htlkaindorf.bigbrain.beans.Rank;
import at.htlkaindorf.bigbrain.beans.RankingHolder;

public class RankingAdapter extends RecyclerView.Adapter<RankingHolder> {
    private List<Rank> rankList = new ArrayList<>();

    public RankingAdapter(List<Rank> rankList) {
        this.rankList = rankList;
    }

    @NonNull
    @Override
    public RankingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
        TextView username = view.findViewById(R.id.tvUserName);
        TextView score = view.findViewById(R.id.tvScore);

        RankingHolder holder = new RankingHolder(view, username, score);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankingHolder holder, int position) {
        Rank rank = rankList.get(position);

        holder.getUsername().setText(rank.getUser().toString());
        holder.getScore().setText(rank.getScore().toString() + "Points");
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }
}
