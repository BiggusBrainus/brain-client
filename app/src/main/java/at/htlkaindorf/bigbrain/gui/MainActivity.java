package at.htlkaindorf.bigbrain.gui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.adapter.AllLobbiesAdapter;
import at.htlkaindorf.bigbrain.api_access.ApiAccess;
import at.htlkaindorf.bigbrain.api_access.JsonResponseListener;
import at.htlkaindorf.bigbrain.beans.Category;
import at.htlkaindorf.bigbrain.beans.Lobby;
import at.htlkaindorf.bigbrain.beans.User;
import at.htlkaindorf.bigbrain.beans.WebSocket;

/**
 * Is the main menu
 * In this activity the user can play multiplayer/solo game
 * and see his rank (user gets forwarded to the specific activity)
 * @version BigBrain v1
 * @since 23.03.2021
 * @author Nico Pessnegger
 */
public class MainActivity extends AppCompatActivity implements JsonResponseListener{
    private final Activity parent = this;

    // Navigationbar
    private ImageButton navigationbarHome;
    //private ImageButton navigationbarUser;
    private ImageButton navigatiobbarRanking;

    // Menu Options
    private Button multiplayer;
    private Button solo;

    // Create User
    private User user = new User();

    // Boolean for solo game
    private boolean lobbyCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Navigationbar
        navigationbarHome = findViewById(R.id.ibNavigationbarHome);
        navigatiobbarRanking = findViewById(R.id.ibNavigationbarRanking);

        // Menu Options
        multiplayer = findViewById(R.id.btMultiplayer);
        solo = findViewById(R.id.btSolo);

        navigationbarHome.setColorFilter(Color.rgb(93, 93, 93));

        // Forward to LoginActivity
        if(user.getToken() == null){
            Intent intent = new Intent(parent, LoginActivity.class);
            intent.putExtra("user", user);
            startActivityForResult(intent, 1);

        }

        // ImageButton to forward to RankingActivity
        navigatiobbarRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, RankingActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        // Button to forward to AllLobiesActivity
        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lobby
                Intent intent = new Intent(parent, AllLobbiesActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("soloGame", false);
                startActivityForResult(intent, 4);
            }
        });

        // Button to play a solo game
        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create lobby
                String url = "https://brain.b34nb01z.club/lobbies/create";
                final JSONObject body = new JSONObject();
                final JSONObject lobby = new JSONObject();
                try {
                    // get random category
                    Random rand = new Random();
                    int category = rand.nextInt(24) + 23;
                    int num = rand.nextInt(1_000_000_000);

                    lobby.put("name", user.getUsername() + num);
                    lobby.put("hidden", true);
                    body.put("token", user.getToken());
                    body.put("lobby", lobby);
                    body.putOpt("categories",new JSONArray(){{ put(category); }});
                } catch (JSONException e) {
                    Log.i("Exception", "Couldn't create lobby or body in OnClickListener of solo button in MainActivity");
                }
                lobbyCreated = true;
                ApiAccess access = new ApiAccess();
                access.getData(url, getApplicationContext(), body, MainActivity.this, Request.Method.POST);
            }
        });
    }

    // To check the reult codes of the Activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            // requestCode of LoginActivity
            case 1:
                user = data.getParcelableExtra("user");
                break;
            // requestCode of
            case 70:
                // Leave Lobby of the GameActivity for the solo game
                final JSONObject body = new JSONObject();
                try {
                    body.put("token", user.getToken());
                } catch (JSONException e) {
                    Log.i("Exception", "Couldn't create body in onActivityResult in MainActivity");
                }
                // User leaves from lobby
                String url = "https://brain.b34nb01z.club/lobbies/leave";
                ApiAccess access = new ApiAccess();
                access.getData(url, parent, body, MainActivity.this, Request.Method.POST);
                break;
        }
    }

    // To start a game (only for solo game)
    public void startGameActivity(){
        // Start Game
        String url = "https://brain.b34nb01z.club/lobbies/start";
        final JSONObject body = new JSONObject();
        try {
            body.put("token", user.getToken());
        } catch (JSONException e) {
            Log.i("Exception", "Couldn't create body in startGameActivity in MainActivity");
        }
        ApiAccess access = new ApiAccess();
        access.getData(url, getApplicationContext(), body, MainActivity.this, Request.Method.POST);

        // Start game activity
        Intent intent = new Intent(parent, GameActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("soloGame", true);
        startActivityForResult(intent, 70);
    }

    @Override
    public void onSuccessJson(String response) {
        JSONObject jObject;
        try {
            jObject = new JSONObject(response);
            // To check if the creation of a lobby was successful
            if((boolean) jObject.get("success") && lobbyCreated){
                lobbyCreated = false;
                // Create Websocket
                WebSocket.bindMain(this);
                WebSocket.createWebSocketClient();
                // Connect user to lobby
                WebSocket.send(String.format("{\"action\" : \"CONNECT_TO_LOBBY\",\"token\" : \"%s\"}", user.getToken()));
            }else if(!((boolean) jObject.get("success")) && lobbyCreated){
                // Lobby name was already taken --> create new lobby
                String url = "https://brain.b34nb01z.club/lobbies/create";
                final JSONObject body = new JSONObject();
                final JSONObject lobby = new JSONObject();
                try {
                    // get random category
                    Random rand = new Random();
                    int category = rand.nextInt(24) + 23;
                    int num = rand.nextInt(1_000_000_000);

                    lobby.put("name", user.getUsername() + num);
                    lobby.put("hidden", true);
                    body.put("token", user.getToken());
                    body.put("lobby", lobby);
                    body.putOpt("categories",new JSONArray(){{ put(category); }});
                } catch (JSONException e) {
                    Log.i("Exception", "Couldn't create lobby or body in onSuccessJson in MainActivity");
                }
                lobbyCreated = true;
                ApiAccess access = new ApiAccess();
                access.getData(url, getApplicationContext(), body, MainActivity.this, Request.Method.POST);
            }
        } catch (JSONException e) {
            Log.i("Exception", "Couldn't find values in JSONObject in MainActivity");
        }
    }
}