package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;

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
    private EditText username;
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
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Um code offline zu testen
                //testen();


                // Login Request
                // Create data for request
                String url = "http://192.168.43.152:8090/auth/login";
                final JSONObject body = new JSONObject();
                try {
                    body.put("username", username.getText());
                    body.put("password",password.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ApiAccess access = new ApiAccess();
                access.getData(url, getApplicationContext(), body, LoginActivity.this, Request.Method.POST);
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
            Log.i("Token: ", jObject.get("token").toString());

            if((boolean) (jObject.get("success"))){
                Intent i = getIntent();
                // TODO change the variables
                User user = i.getParcelableExtra("user");
                user.setUsername(username.getText().toString());
                user.setEmail("asdfasdfasdf");
                user.setToken(jObject.get("token").toString());
                Intent intent = new Intent();
                intent.putExtra("user", user);
                setResult(1, intent);
                finish();
            }else{
                Log.i("Error: ", jObject.get("error").toString());
                switch(jObject.get("error").toString()){
                    //TODO error handle message
                    case "UNKNOWN_CREDS":
                        this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Username or Password is wrong", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    default:
                        this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "An error occurred, check your WIFI or try it later", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // TODO can be deleted. Only purpose is to test without connection to server
    public void testen(){
        Intent i = getIntent();
        User user = i.getParcelableExtra("user");
        user.setUsername(username.getText().toString());
        user.setEmail("asdfasdfasdf");
        user.setToken("wowEineToken");
        Intent intent = new Intent();
        intent.putExtra("user", user);
        setResult(1, intent);
        finish();
    }
}
