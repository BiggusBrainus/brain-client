package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

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

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.adapter.AllLobbiesAdapter;
import at.htlkaindorf.bigbrain.api_access.ApiAccess;
import at.htlkaindorf.bigbrain.api_access.JsonResponseListener;
import at.htlkaindorf.bigbrain.beans.Lobby;
import at.htlkaindorf.bigbrain.beans.User;

/*
* Author:   Nico Pessnegger
* Created:  25.05.2021
* Project:  BigBrain
* */
public class AllLobbiesActivity extends AppCompatActivity implements JsonResponseListener {
    private final Activity parent = this;

    // Buttons
    private Button exit;
    private Button create;
    private Button joinPrivate;

    // SearchView
    private SearchView searchbar;

    // RecyclerView
    private RecyclerView lobbies;

    // Adapter
    private AllLobbiesAdapter ala;

    // User
    private User user;

    // Thread
    private Thread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lobbies);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        joinPrivate = findViewById(R.id.btJoinPrivate);
        exit = findViewById(R.id.btExitAllLobbies);
        create = findViewById(R.id.btCreate);
        searchbar = findViewById(R.id.svSearchbar);
        lobbies = findViewById(R.id.rvLobbies);

        // Get user variable
        Intent i = getIntent();
        user = i.getParcelableExtra("user");

        // Login Request
        // Create data for request
        String url = "https://brain.b34nb01z.club/lobbies/get";

        ApiAccess access = new ApiAccess();
        // Thread to make a request every second
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    access.getData(url, getApplicationContext(), AllLobbiesActivity.this, Request.Method.GET);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.i("Exception", "Thread Interrupted in AllLobbiesActivity");
                        break;
                    }
                }
            }
        });
        thread.start();

        // Button to join a private lobby
        joinPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(parent);
                View privateLobby = li.inflate(R.layout.altert_private_lobby, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(privateLobby);

                final EditText userInput = (EditText) privateLobby
                        .findViewById(R.id.etUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        String lobbyName = userInput.getText().toString();
                                        if(!lobbyName.equals("")){
                                            sendJoinRequest(lobbyName);
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show alert dialog
                alertDialog.show();

            }
        });

        // Button to exit the AllLobbiesActivityScreen
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thread.interrupt();
                finish();
            }
        });

        // Button to create a lobby
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, CreateLobbyActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("soloGame", false);
                startActivityForResult(intent, 42);
            }
        });

        // To use onQueryTextChange not only if the search icon is pressed
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbar.setIconified(false);
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
            Log.i("Exception", "Couldn't set token or body in AllLobbiesActivity");
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
            try {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Lobby>>() {}.getType();
                // If no "lobbies" in jObject --> Error
                List<Lobby> lobbyList = gson.fromJson(jObject.get("lobbies").toString(), listType);
                lobbies.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
                ala = new AllLobbiesAdapter(this, lobbyList, user);
                lobbies.setAdapter(ala);
                ala.filterLobbies(searchbar.getQuery().toString());
            } catch (JSONException e) {
                // If no "lobbies" in Object but "success" is true --> forward to WaitingRoom
                if((boolean) jObject.get("success")){
                    thread.interrupt();
                    Intent intent = new Intent(parent, WaitingRoomActivity.class);
                    intent.putExtra("user", user);
                    startActivityForResult(intent, 9);
                }
            }
        } catch (JSONException e) {
            Log.i("Exception", "Couldn't find lobbies in JSONObject in AllLobbiesActivity");
        }
    }

    // To check the reult codes of the Activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            // requestCode of WaitingRoomActivity
            case 9:
                if (data.getStringExtra("exit").equals("menu")){
                    finish();
                }
                break;
            // requestCode of CreateLobbyActivity
            case 42:
                if (!data.getStringExtra("exit").equals("lobby")){
                    Intent i = getIntent();
                    // Forward to WaitingRoom
                    Intent intent = new Intent(parent, WaitingRoomActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("soloGame", i.getBooleanExtra("soloGame", false));
                    startActivityForResult(intent, 9);
                }
                break;
        }
    }
}
