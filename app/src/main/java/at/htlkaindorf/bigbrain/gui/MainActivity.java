package at.htlkaindorf.bigbrain.gui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.beans.User;
import tech.gusavila92.websocketclient.WebSocketClient;

public class MainActivity extends AppCompatActivity {
    private final Activity parent = this;

    // Navigationbar
    private ImageButton navigationbarHome;
    private ImageButton navigationbarUser;
    private ImageButton navigatiobbarSettings;

    // Menu Options
    private Button multiplayer;
    private Button offlineplayer;
    private Button statistics;

    // Create User
    private User user = new User();

    // Websocket
    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createWebSocketClient();

        // Navigationbar
        navigationbarHome = findViewById(R.id.ibNavigationbarHome);
        navigationbarUser = findViewById(R.id.ibNavigationbarUser);
        navigatiobbarSettings = findViewById(R.id.ibNavigationbarSettings);

        // Menu Options
        multiplayer = findViewById(R.id.btMultiplayer);
        offlineplayer = findViewById(R.id.btOfflineplayer);
        statistics = findViewById(R.id.btStatistics);

        navigationbarHome.setColorFilter(Color.rgb(93, 93, 93));



        // If User is not loged in
        //if(user == null){
            Intent intent = new Intent(parent, LoginActivity.class);
            intent.putExtra("user", user);
            startActivityForResult(intent, 1);

        //}


        navigationbarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, UserActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, 2);
            }
        });

        navigatiobbarSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, SettingsActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lobby
                Intent intent = new Intent(parent, AllLobbiesActivity.class);
                startActivityForResult(intent, 4);
            }
        });

        offlineplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Auf Offline Screen wechseln
                Log.i("Test", "Offline");
                // TODO
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Auf Statistics Screen wechseln
                Log.i("Test", "Statistics");
                // TODO
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            String newActivity = data.getStringExtra("activity");
            if(newActivity.equals("user")){
                Intent intent = new Intent(parent, UserActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, 2);
            }else if(newActivity.equals("settings")){
                Intent intent = new Intent(parent, SettingsActivity.class);
                startActivityForResult(intent, 2);
            }
        }else if(requestCode == 1){
            user = data.getParcelableExtra("user");
        }else if(requestCode == 4){
            // Start the game
            Intent intent = new Intent(parent, GameActivity.class);
            intent .putExtra("rounds", 3);
            startActivity(intent);
        }
    }

    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://192.168.43.152:8090/ws/hello");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
                webSocketClient.send("Hello World!");
            }
            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received");
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Log.i("Test", "Wow ich bin da");
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onBinaryReceived(byte[] data) {

            }

            @Override
            public void onPingReceived(byte[] data) {

            }

            @Override
            public void onPongReceived(byte[] data) {

            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
                System.out.println("onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    public void sendMessage(View view) {
        Log.i("WebSocket", "Button was clicked");
        // Send button id string to WebSocket Server
        switch(view.getId()){
            case(R.id.btMultiplayer):
                webSocketClient.send("Online");
                break;
            case(R.id.btOfflineplayer):
                webSocketClient.send("Offline");
                break;
            case(R.id.btStatistics):
                webSocketClient.send("Statistics");
                break;
        }
    }
}