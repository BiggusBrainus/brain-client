package at.htlkaindorf.bigbrain.gui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import at.htlkaindorf.bigbrain.R;

public class LoginActivity extends AppCompatActivity {
    // Button
    private Button login;
    private Button signup;

    // Text
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Button
        login = findViewById(R.id.btLogin);
        signup = findViewById(R.id.btSignup);

        // Text
        email = findViewById(R.id.etEmailUser);
        password = findViewById(R.id.etPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                finish();
            }
        });
    }
}
