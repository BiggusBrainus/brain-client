package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        // RequestQue
        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.43.152:8090/auth/register";
                final JSONObject body = new JSONObject();
                try {
                    body.put("username", name.getText());
                    body.put("email", email.getText());
                    body.put("password",password.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ApiAccess access = new ApiAccess();
                access.getData(url, getApplicationContext(), body, RegisterActivity.this, Request.Method.POST);
                finish();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
