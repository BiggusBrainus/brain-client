package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.adapter.AllLobbiesAdapter;
import at.htlkaindorf.bigbrain.api_access.ApiAccess;
import at.htlkaindorf.bigbrain.api_access.JsonResponseListener;
import at.htlkaindorf.bigbrain.beans.Lobby;
import at.htlkaindorf.bigbrain.beans.User;

public class AllLobbiesActivity extends AppCompatActivity implements JsonResponseListener {
    private final Activity parent = this;

    // Buttons
    private Button join;
    private Button create;

    // SearchView
    private SearchView searchbar;

    // RecyclerView
    private RecyclerView lobbies;

    // Adapter
    private AllLobbiesAdapter ala;

    // Executor
    private Executor executor;

    // User
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lobbies);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        join = findViewById(R.id.btJoin);
        create = findViewById(R.id.btCreate);
        searchbar = findViewById(R.id.svSearchbar);
        lobbies = findViewById(R.id.rvLobbies);

        // Get user variable
        Intent i = getIntent();
        user = i.getParcelableExtra("user");

        // Login Request
        // Create data for request
        String url = "https://brain.b34nb01z.club/lobbies/get";
        final JSONObject body = new JSONObject();

        ApiAccess access = new ApiAccess();
        executor = this.getMainExecutor();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    access.getData(url, getApplicationContext(), AllLobbiesActivity.this, Request.Method.GET);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        // Um code offline zu testen
        //testen();

        // TODO
        lobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("test", "Activity");
                //TODO
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, CreateLobbyActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, 42);
            }
        });

        // To use onQueryTextChange not only if the search icon is pressed
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ala.filterLobbies(s);
                return false;
            }
        });
    }

    // To use .onActivityResult() the intent has to be sent from an Activity
    public void sendJoinRequest(String lobbyName) {
        // Request to get lobby token
        final JSONObject body = new JSONObject();
        try {
            body.put("token", user.getToken());
            body.put("lobby", lobbyName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://brain.b34nb01z.club/lobbies/join";
        ApiAccess access = new ApiAccess();
        access.getData(url, parent, body, AllLobbiesActivity.this, Request.Method.POST);
    }

    @Override
    public void onSuccessJson(String response) {
        JSONObject jObject;
        try {
            jObject = new JSONObject(response);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Lobby>>() {}.getType();
            // If no "lobbies" in jObject --> Error
            List<Lobby> lobbyList = gson.fromJson(jObject.get("lobbies").toString(), listType);
            lobbies.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
            ala = new AllLobbiesAdapter(this, lobbyList, user);
            lobbies.setAdapter(ala);
            ala.filterLobbies(searchbar.getQuery().toString());
        } catch (JSONException e) {
            // Weiterleiten zum WaitingRoom
            Intent intent = new Intent(parent, WaitingRoomActivity.class);
            intent.putExtra("user", user);
            startActivityForResult(intent, 9);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 9:
                if (data.getStringExtra("exit").equals("menu")){
                    finish();
                }
                break;
            case 42:
                // Weiterleiten zum WaitingRoom
                Intent intent = new Intent(parent, WaitingRoomActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, 9);
                break;
        }
    }

    // TODO just to test (without connection to the server)
    public void testen(){
        User u = new User("Hermann", "hermann@gmail.com", "WowEinToken");
        List<User> ul = new ArrayList<>();
        ul.add(u);

        Lobby l = new Lobby("Lobby1", ul, null, true);
        List<Lobby> ll= new ArrayList<>();
        ll.add(l);
        lobbies.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        lobbies.setAdapter(ala = new AllLobbiesAdapter(this, ll, user));
        ala.filterLobbies(searchbar.getQuery().toString());
    }
}
