package at.htlkaindorf.bigbrain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Navigationbar
        navigationbarHome = findViewById(R.id.ibNavigationbarHome);
        navigationbarUser = findViewById(R.id.ibNavigationbarUser);
        navigatiobbarSettings = findViewById(R.id.ibNavigationbarSettings);

        // Menu Options
        multiplayer = findViewById(R.id.btMultiplayer);
        offlineplayer = findViewById(R.id.btOfflineplayer);
        statistics = findViewById(R.id.btStatistics);


        // If User in not loged in
        //Intent intet = new Intent(parent, LoginActivity.class);
        //startActivity(intet);

        navigationbarHome.setColorFilter(Color.rgb(93, 93, 93));


        navigationbarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, UserActivity.class);
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
                // Auf Online Screen wechseln
                Log.i("Test", "Online");
                // TODO
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
                startActivityForResult(intent, 2);
            }else if(newActivity.equals("settings")){
                Intent intent = new Intent(parent, SettingsActivity.class);
                startActivityForResult(intent, 2);
            }
        }
    }
}