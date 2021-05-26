package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import at.htlkaindorf.bigbrain.R;

public class AllLobbiesActivity extends AppCompatActivity {
    private final Activity parent = this;

    // Buttons
    private Button join;
    private Button create;

    // SearchView
    private SearchView searchbar;

    // RecyclerView
    private RecyclerView lobbies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lobbies);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        join = findViewById(R.id.btJoin);
        create = findViewById(R.id.btCreate);
        searchbar = findViewById(R.id.svSearchbar);
        lobbies = findViewById(R.id.rvLobbies);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, WaitingRoomActivity.class);
                startActivity(intent);
                Intent i = getIntent();
                i.putExtra("lobbyName", "Lobby1");
                setResult(4, intent);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
