package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.adapter.AllLobbiesAdapter;
import at.htlkaindorf.bigbrain.adapter.RankingAdapter;
import at.htlkaindorf.bigbrain.adapter.WaitingRoomAdapter;
import at.htlkaindorf.bigbrain.api_access.ApiAccess;
import at.htlkaindorf.bigbrain.api_access.JsonResponseListener;
import at.htlkaindorf.bigbrain.beans.Lobby;
import at.htlkaindorf.bigbrain.beans.Rank;
import at.htlkaindorf.bigbrain.beans.User;

public class RankingActivity extends AppCompatActivity implements JsonResponseListener {
    private RecyclerView ranking;
    private ImageButton navigationbarHome;
    private ImageButton navigationbarRanking;

    // Adapter
    private RankingAdapter ra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ranking = findViewById(R.id.rvRanking);
        navigationbarHome = findViewById(R.id.ibNavigationbarHome);
        navigationbarRanking = findViewById(R.id.ibNavigationbarRanking);

        navigationbarRanking.setColorFilter(Color.rgb(93, 93, 93));


        //testen();
        String url = "https://brain.b34nb01z.club/ranking/get";
        final JSONObject body = new JSONObject();

        ApiAccess access = new ApiAccess();
        access.getData(url, getApplicationContext(), RankingActivity.this, Request.Method.GET);

        navigationbarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onSuccessJson(String response) {
        JSONObject jObject;
        try {
            jObject = new JSONObject(response);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Rank>>() {}.getType();
            List<Rank> rankList = gson.fromJson(jObject.get("ranking").toString(), listType);
            ranking.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            ranking.setAdapter(ra = new RankingAdapter(rankList, "Wins"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // TODO just to test (without connection to the server)
    public void testen(){
        User u = new User("Hermann", "hermann@gmail.com", "WowEinToken");
        Rank r = new Rank(u, (long) 10000);
        List<Rank> rl = new ArrayList<>();
        rl.add(r);
        ranking.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ranking.setAdapter(ra = new RankingAdapter(rl, "Wins"));
    }
}
