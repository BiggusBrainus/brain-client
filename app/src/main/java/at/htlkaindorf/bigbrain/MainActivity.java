package at.htlkaindorf.bigbrain;

import androidx.appcompat.app.AppCompatActivity;

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

    // Toolbar
    private ImageButton toolbarHome;
    private ImageButton toolbarUser;
    private ImageButton toolbarSettings;

    // Menu Options
    private Button multiplayer;
    private Button offlineplayer;
    private Button statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbarHome = findViewById(R.id.ibToolbarHome);
        toolbarUser = findViewById(R.id.ibToolbarUser);
        toolbarSettings = findViewById(R.id.ibToolbarSettings);

        // Menu Options
        multiplayer = findViewById(R.id.btMultiplayer);
        offlineplayer = findViewById(R.id.btOfflineplayer);
        statistics = findViewById(R.id.btStatistics);


        // If User in not loged in
        Intent intet = new Intent(parent, LoginActivity.class);
        startActivity(intet);

        toolbarHome.setColorFilter(Color.rgb(93, 93, 93));


        toolbarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Auf User Screen wechseln
                Log.i("Test", "User");
                // TODO

            }
        });

        toolbarSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Auf Settings Screen wechseln
                Log.i("Test", "Settings");
                // TODO
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
}