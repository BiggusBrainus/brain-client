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

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.beans.User;

public class MainActivity extends AppCompatActivity {
    private final Activity parent = this;

    // Navigationbar
    private ImageButton navigationbarHome;
    //private ImageButton navigationbarUser;
    private ImageButton navigatiobbarRanking;

    // Menu Options
    private Button multiplayer;
    private Button offlineplayer;
    private Button statistics;

    // Create User
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Navigationbar
        navigationbarHome = findViewById(R.id.ibNavigationbarHome);
        //navigationbarUser = findViewById(R.id.ibNavigationbarUser);
        navigatiobbarRanking = findViewById(R.id.ibNavigationbarRanking);

        // Menu Options
        multiplayer = findViewById(R.id.btMultiplayer);
        offlineplayer = findViewById(R.id.btOfflineplayer);
        statistics = findViewById(R.id.btStatistics);

        navigationbarHome.setColorFilter(Color.rgb(93, 93, 93));



        // If User is not loged in
        if(user.getToken() == null){
            Intent intent = new Intent(parent, LoginActivity.class);
            intent.putExtra("user", user);
            startActivityForResult(intent, 1);

        }


        /*navigationbarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, UserActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, 2);
            }
        });*/

        navigatiobbarRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, RankingActivity.class);
                startActivity(intent);
            }
        });

        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lobby
                Intent intent = new Intent(parent, AllLobbiesActivity.class);
                intent.putExtra("user", user);
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
        switch (requestCode){
            case 1:
                user = data.getParcelableExtra("user");
                Log.i("test", user.getToken());
                break;
            case 2:
                String newActivity = data.getStringExtra("activity");
                if(newActivity.equals("user")){
                    Intent intent = new Intent(parent, UserActivity.class);
                    intent.putExtra("user", user);
                    startActivityForResult(intent, 2);
                }else if(newActivity.equals("settings")){
                    Intent intent = new Intent(parent, SettingsActivity.class);
                    startActivityForResult(intent, 2);
                }
                break;
        }
    }


}