package at.htlkaindorf.bigbrain.adapter;

import android.app.DownloadManager;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.api_access.ApiAccess;
import at.htlkaindorf.bigbrain.api_access.JsonResponseListener;
import at.htlkaindorf.bigbrain.beans.Lobby;
import at.htlkaindorf.bigbrain.beans.LobbyHolder;
import at.htlkaindorf.bigbrain.beans.User;
import at.htlkaindorf.bigbrain.gui.AllLobbiesActivity;
import at.htlkaindorf.bigbrain.gui.WaitingRoomActivity;

public class AllLobbiesAdapter extends RecyclerView.Adapter<LobbyHolder> implements JsonResponseListener {
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
                Log.i("test", view.toString());
                TextView lobbyName = view.findViewById(R.id.tvLobbyName);
                // Request to get lobby token
                final JSONObject body = new JSONObject();
                try {
                    body.put("token", user.getToken());
                    Log.i("token", user.getToken());
                    body.put("lobby", lobbyName.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = "http://192.168.43.152:8090/lobbies/join";
                ApiAccess access = new ApiAccess();
                access.getData(url, parent, body, AllLobbiesAdapter.this, Request.Method.POST);


            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public void onSuccessJson(String response) {
        JSONObject jObject;
        try {
            jObject = new JSONObject(response);
            // Weiterleiten zum WaitingRoom
            Intent intent = new Intent(parent, WaitingRoomActivity.class);
            intent.putExtra("user", user);
            parent.startActivity(intent);
            parent.finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
