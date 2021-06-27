package at.htlkaindorf.bigbrain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.holder.WaitingRoomHolder;

/**
 * Adapter for the RecyclerView in the WaitingRoomActivity
 * @version BigBrain v1
 * @since 02.06.2021
 * @author Nico Pessnegger
 */
public class WaitingRoomAdapter extends RecyclerView.Adapter<WaitingRoomHolder> {
    private List<String> players;

    public WaitingRoomAdapter(List<String> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public WaitingRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        TextView playerName = view.findViewById(R.id.tvPlayerName);

        WaitingRoomHolder holder = new WaitingRoomHolder(view, playerName);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingRoomHolder holder, int position) {
        String player = players.get(position);

        holder.getPlayername().setText(player);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
