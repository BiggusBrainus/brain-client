package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.api_access.ApiAccess;
import at.htlkaindorf.bigbrain.api_access.JsonResponseListener;
import at.htlkaindorf.bigbrain.beans.User;

public class LoginActivity extends AppCompatActivity implements JsonResponseListener {
    // Button
    private Button login;

    // Text
    private EditText email;
    private EditText password;
    private TextView registerNow;

    // Activity
    private final Activity parent = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Button
        login = findViewById(R.id.btLogin);
        registerNow = findViewById(R.id.tvRegisterNow);

        // Text
        email = findViewById(R.id.etEmailUser);
        password = findViewById(R.id.etPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                // TODO change the variables
                User user = i.getParcelableExtra("user");
                user.setEmail(email.getText().toString());
                user.setUsername("LOL");
                Intent intent = new Intent();
                intent.putExtra("user", user);
                setResult(1, intent);

                // Login Request
                // Create data for request
                String url = "http://172.20.10.6:8080/api/user/login";
                final JSONObject body = new JSONObject();
                try {
                    body.put("username", "asdf");
                    body.put("password","asdf");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", email.getText().toString());
                params.put("password", password.getText().toString());
                ApiAccess access = new ApiAccess();
                access.getData(url, getApplicationContext(), body, params, LoginActivity.this);

                finish();
            }
        });

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSuccessJson(String response) {
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(response);
            Log.i("Success: ", jObject.get("success").toString());
            Log.i("UUID: ", jObject.get("uuid").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
