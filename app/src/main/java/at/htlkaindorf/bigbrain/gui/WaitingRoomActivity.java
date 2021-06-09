package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.adapter.AllLobbiesAdapter;
import at.htlkaindorf.bigbrain.adapter.WaitingRoomAdapter;
import at.htlkaindorf.bigbrain.api_access.ApiAccess;
import at.htlkaindorf.bigbrain.api_access.JsonResponseListener;
import at.htlkaindorf.bigbrain.beans.Lobby;
import at.htlkaindorf.bigbrain.beans.User;
import at.htlkaindorf.bigbrain.beans.WebSocket;
import tech.gusavila92.websocketclient.WebSocketClient;

public class WaitingRoomActivity extends AppCompatActivity implements JsonResponseListener {
    // Buttons
    private Button start;

    // RecyclerView
    private RecyclerView players;

    // Adapter
    private WaitingRoomAdapter wra;

    // Activity
    private Activity parent = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);
        WebSocket.bindWaitingRoom(this);
        WebSocket.createWebSocketClient();

        start = findViewById(R.id.btStart);
        players = findViewById(R.id.rvPlayers);

        Intent i = getIntent();
        User user = i.getParcelableExtra("user");

        WebSocket.send(String.format("{\"action\" : \"CONNECT_TO_LOBBY\",\"token\" : \"%s\"}", user.getToken()));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = i.getParcelableExtra("user");
                String url = "http://192.168.43.152:8090/lobbies/start";
                final JSONObject body = new JSONObject();
                try {
                    body.put("token", user.getToken());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ApiAccess access = new ApiAccess();
                access.getData(url, getApplicationContext(), body, WaitingRoomActivity.this, Request.Method.POST);
            }
        });
    }

    public void updatePlayer(JSONObject jObject) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                List<User> playerList = null;
                try {
                    Log.i("asdf",jObject.get("players").toString());
                    playerList = gson.fromJson(jObject.get("players").toString(), listType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                players.setLayoutManager(new LinearLayoutManager(parent.getApplicationContext()));
                players.setAdapter(wra = new WaitingRoomAdapter(playerList.stream().map(User::getUsername).collect(Collectors.toList())));
            }
        });
    }

    public void startGame(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(parent, GameActivity.class);
                intent.putExtra("rounds", 3);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onSuccessJson(String response) {

    }
}
