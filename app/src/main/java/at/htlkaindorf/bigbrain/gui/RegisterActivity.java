package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import at.htlkaindorf.bigbrain.R;
import at.htlkaindorf.bigbrain.api_access.ApiAccess;
import at.htlkaindorf.bigbrain.api_access.JsonResponseListener;

/**
 * User can create a new account
 * @version BigBrain v1
 * @since 11.05.2021
 * @author Nico Pessnegger
 */
public class RegisterActivity extends AppCompatActivity implements JsonResponseListener {
    // Button
    private Button register;
    private Button goBack;

    // Text
    private EditText name;
    private EditText email;
    private EditText password;

    // Activity
    private Activity parent = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Button
        register = findViewById(R.id.btRegister);
        goBack= findViewById(R.id.btGoBack);

        // Text
        name = findViewById(R.id.etRegisterUsername);
        email = findViewById(R.id.etRegisterEmail);
        password = findViewById(R.id.etRegisterPassword);

        // Button to creat a new User
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Username, Email or Password is invalid", Toast.LENGTH_LONG).show();
                }else{
                    String url = "https://brain.b34nb01z.club/auth/register";
                    final JSONObject body = new JSONObject();
                    try {
                        body.put("username", name.getText());
                        body.put("email", email.getText());
                        body.put("password",password.getText());
                    } catch (JSONException e) {
                        Log.i("Exception", "Couldn't create body in RegisterActivity");
                    }
                    ApiAccess access = new ApiAccess();
                    access.getData(url, getApplicationContext(), body, RegisterActivity.this, Request.Method.POST);
                    finish();
                    }
                }
        });

        // Button to exit the RegisterActivity --> back to the LoginActivity
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onSuccessJson(String response) {
        // onSuccessJson needs to be implemented, but is not used in this Activity
    }
}
