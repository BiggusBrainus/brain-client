package at.htlkaindorf.bigbrain.gui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import at.htlkaindorf.bigbrain.R;

public class WaitingRoomActivity extends AppCompatActivity {
    // Buttons
    private Button start;

    // RecyclerView
    private RecyclerView players;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);

        start = findViewById(R.id.btStart);
        players = findViewById(R.id.rvPlayers);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
