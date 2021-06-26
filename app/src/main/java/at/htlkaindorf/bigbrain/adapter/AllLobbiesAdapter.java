package at.htlkaindorf.bigbrain.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.beans.Lobby;
import at.htlkaindorf.bigbrain.beans.LobbyHolder;
import at.htlkaindorf.bigbrain.beans.User;
import at.htlkaindorf.bigbrain.gui.AllLobbiesActivity;

public class AllLobbiesAdapter extends RecyclerView.Adapter<LobbyHolder> {
    private AllLobbiesActivity parent;
    private List<Lobby> lobbyList;
    private List<Lobby> filteredList;
    private User user;

    public AllLobbiesAdapter(AllLobbiesActivity parent, List<Lobby> lobbyList, User user) {
        this.parent = parent;
        this.lobbyList = lobbyList;
        this.filteredList = new ArrayList<>(lobbyList);
        this.user = user;
    }

    public void filterLobbies(String str){
        str = str.trim();
        // It is not possible to change the data in the lambda expression
        final String strForLambda = str.toLowerCase();
        if(str.equals("")){
            filteredList.clear();
            filteredList.addAll(lobbyList);
        }else {
            filteredList.removeIf(l -> !(l.getName().toLowerCase().contains(strForLambda)));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LobbyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lobby_item, parent, false);
        TextView lobbyName = view.findViewById(R.id.tvLobbyName);
        TextView amuountOfPlayers = view.findViewById(R.id.tvAmountOfPlayers);

        LobbyHolder holder = new LobbyHolder(view, lobbyName, amuountOfPlayers);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LobbyHolder holder, int position) {
        Lobby lobby = filteredList.get(position);

        holder.getLobbyName().setText(lobby.getName());
        holder.getAmountOfPlayers().setText(lobby.getPlayers().size() + " / 10");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView lobbyName = view.findViewById(R.id.tvLobbyName);
                parent.sendJoinRequest(lobbyName.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }
}
