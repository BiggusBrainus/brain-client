package at.htlkaindorf.bigbrain.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.adapter.AllLobbiesAdapter;
import at.htlkaindorf.bigbrain.api_access.ApiAccess;
import at.htlkaindorf.bigbrain.api_access.JsonResponseListener;
import at.htlkaindorf.bigbrain.beans.Category;
import at.htlkaindorf.bigbrain.beans.Lobby;
import at.htlkaindorf.bigbrain.beans.User;

/*
 * Author:   Nico Pessnegger
 * Created:  08.06.2021
 * Project:  BigBrain
 * */
public class CreateLobbyActivity extends AppCompatActivity implements JsonResponseListener {
    private EditText lobbyName;
    private Spinner categories;

    private Button create;
    private Button exit;
    private CheckBox privateLobby;

    private List<Category> categoryList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        lobbyName = findViewById(R.id.etLobbyName);
        categories = findViewById(R.id.spCategories);
        create = findViewById(R.id.btCreateLobby);
        exit = findViewById(R.id.btExitCreateLobby);
        privateLobby = findViewById(R.id.cbPrivateLobby);

        // Request to get all lobby categories
        String url = "https://brain.b34nb01z.club/lobbies/categories";
        ApiAccess access = new ApiAccess();
        access.getData(url, getApplicationContext(), CreateLobbyActivity.this, Request.Method.GET);

        // Button to create a new lobby
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user variable
                Intent i = getIntent();
                User user = i.getParcelableExtra("user");

                // Create data for request
                String url = "https://brain.b34nb01z.club/lobbies/create";
                final JSONObject body = new JSONObject();
                final JSONObject lobby = new JSONObject();
                try {
                    lobby.put("name", lobbyName.getText().toString());
                    lobby.put("hidden", privateLobby.isChecked());
                    body.put("token", user.getToken());
                    body.put("lobby", lobby);
                    body.putOpt("categories",new JSONArray(){{ put(categoryList.get(categoryList.stream().map(Category::getTitle).collect(Collectors.toList()).indexOf(categories.getSelectedItem().toString())).getCid()); }});
                } catch (JSONException e) {
                    Log.i("Exception", "Couldn't create the body when create button was pressed in CreateLobbyActivity");
                }
                ApiAccess access = new ApiAccess();
                access.getData(url, getApplicationContext(), body, CreateLobbyActivity.this, Request.Method.POST);
            }
        });

        // Button to exit the activity
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("exit", "lobby");
                setResult(42, intent);
                finish();
            }
        });
    }

    @Override
    public void onSuccessJson(String response) {
        JSONObject jObject;
        try {
            jObject = new JSONObject(response);
            try {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Category>>() {}.getType();
                categoryList = gson.fromJson(jObject.get("categories").toString(), listType);
                String[] paths = categoryList.stream().map(Category::getTitle).toArray(String[]::new);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateLobbyActivity.this, android.R.layout.simple_spinner_item,paths);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categories.setAdapter(adapter);
            } catch (JSONException e) {
                // If no "categories" in Object but "success" is true --> finish the activity
                if((boolean) jObject.get("success")){
                    Intent intent = new Intent();
                    // To check if game should be started right away (only necessary for the solo game)
                    intent.putExtra("exit", "StehtDaDamitEsNichtNullIst");
                    setResult(42, intent);
                    finish();
                }
            }
        } catch (JSONException e) {
            Log.i("Exception", "Error occurred in onSucessJson in CreateLobbyActivity");
        }
    }

    // If user uses swipes to exit the activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("exit", "lobby");
        setResult(42, intent);
        finish();
    }
}
