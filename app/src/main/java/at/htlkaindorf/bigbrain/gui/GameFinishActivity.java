package at.htlkaindorf.bigbrain.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.adapter.RankingAdapter;
import at.htlkaindorf.bigbrain.beans.Rank;

public class GameFinishActivity extends AppCompatActivity {
    private RecyclerView ranking;
    private Button exitToMenu;
    private Button exitToLobby;

    private RankingAdapter ra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finish);

        ranking = findViewById(R.id.rvGameFinishRank);
        exitToMenu = findViewById(R.id.btExitToMenu);
        exitToLobby = findViewById(R.id.btExitToLobby);

        Intent i = getIntent();
        if(i.getBooleanExtra("soloGame", false)){
            Intent intent = new Intent();
            intent.putExtra("exit", "menu");
            setResult(11, intent);
            finish();
        }
        List<Rank> rankList = i.getParcelableArrayListExtra("ranking");
        ranking.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        ranking.setAdapter(ra = new RankingAdapter(rankList, "Points"));

        exitToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("exit", "menu");
                setResult(11, intent);
                finish();
            }
        });

        exitToLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("exit", "lobby");
                setResult(11, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("exit", "lobby");
        setResult(11, intent);
        finish();
    }
}
