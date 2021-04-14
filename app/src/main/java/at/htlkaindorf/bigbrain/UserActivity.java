package at.htlkaindorf.bigbrain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    private Activity parentUser = this;

    // Navigationbar
    private ImageButton navigationbarHome;
    private ImageButton navigationbarUser;
    private ImageButton navigatiobbarSettings;

    // Text
    private EditText userName;
    private EditText userEmail;
    private EditText userAge;

    // Button
    private Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Navigationbar
        navigationbarHome = findViewById(R.id.ibNavigationbarHome);
        navigationbarUser = findViewById(R.id.ibNavigationbarUser);
        navigatiobbarSettings = findViewById(R.id.ibNavigationbarSettings);

        // Text
        userName = findViewById(R.id.etNameUser);
        userEmail = findViewById(R.id.etEmailUser);
        userAge = findViewById(R.id.etAgeUser);

        // Button
        save = findViewById(R.id.btSave);

        navigationbarUser.setColorFilter(Color.rgb(93,93,93));

        // Intent
        Intent intent = new Intent();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        navigationbarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("activity", "home");
                setResult(2, intent);
                finish();
            }
        });

        navigatiobbarSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("activity", "settings");
                setResult(2, intent);
                finish();
            }
        });
    }
}
