package at.htlkaindorf.bigbrain.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import at.htlkaindorf.bigbrain.R;

public class SettingsActivity extends AppCompatActivity {
    // Navigationbar
    private ImageButton navigationbarHome;
    private ImageButton navigationbarUser;
    private ImageButton navigatiobbarSettings;

    // Seekbar
    private SeekBar seekbarMasterVolume;
    private SeekBar seekbarRightVolume;
    private SeekBar seekbarWrongVolume;

    // Text
    private TextView textMasterVolume;
    private TextView textRightVolume;
    private TextView textWrongVolume;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Navigationbar
        navigationbarHome = findViewById(R.id.ibNavigationbarHome);
        navigationbarUser = findViewById(R.id.ibNavigationbarUser);
        navigatiobbarSettings = findViewById(R.id.ibNavigationbarRanking);

        // Seekbar
        seekbarMasterVolume = findViewById(R.id.sbMasterVloume);
        seekbarRightVolume = findViewById(R.id.sbRightAnswerVolume);
        seekbarWrongVolume = findViewById(R.id.sbWrongAnswerVolume);

        // Text
        textMasterVolume = findViewById(R.id.tvMasterVolume);
        textRightVolume = findViewById(R.id.tvRightAnswerVolume);
        textWrongVolume = findViewById(R.id.tvWrongAnswerVolume);

        navigatiobbarSettings.setColorFilter(Color.rgb(93,93,93));

        // Intent
        Intent intent = new Intent();

        navigationbarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("activity", "home");
                setResult(2, intent);
                finish();
            }
        });

        navigationbarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("activity", "user");
                setResult(2, intent);
                finish();
            }
        });
    }
}
