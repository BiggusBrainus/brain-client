package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import at.htlkaindorf.bigbrain.adapter.AllLobbiesAdapter;
import at.htlkaindorf.bigbrain.adapter.WaitingRoomAdapter;
import at.htlkaindorf.bigbrain.beans.Lobby;
import at.htlkaindorf.bigbrain.beans.Question;
import at.htlkaindorf.bigbrain.beans.Rank;
import at.htlkaindorf.bigbrain.beans.User;
import at.htlkaindorf.bigbrain.beans.WebSocket;
import at.htlkaindorf.bigbrain.bl.ButtonSize;
import at.htlkaindorf.bigbrain.bl.Game;
import at.htlkaindorf.bigbrain.R;

public class GameActivity extends AppCompatActivity {
    // Text
    private TextView question;
    private TextView round;
    private TextView timer;

    // Button
    private Button topLeft;
    private Button topRight;
    private Button bottomLeft;
    private Button bottomRight;

    // Game
    private Game game = new Game();

    // Activity
    private Activity parent = this;

    // Enum
    private ButtonSize buttonEnum;

    // int
    private int counter = 0;

    // Question
    String rightAnswer = "";
    List<String> wrong;
    List<Button> buttonList = new ArrayList<>();
    User user;
    boolean firstQuestion = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Text
        question = findViewById(R.id.tvQuestion);
        round = findViewById(R.id.tvRound);
        timer = findViewById(R.id.tvTimer);
        timer.setText("");

        // Button
        topLeft = findViewById(R.id.btTopLeft);
        topRight = findViewById(R.id.btTopRight);
        bottomLeft = findViewById(R.id.btBottomLeft);
        bottomRight = findViewById(R.id.btBottomRight);

        buttonList.add(topLeft);
        buttonList.add(topRight);
        buttonList.add(bottomLeft);
        buttonList.add(bottomRight);

        // Get user variable
        Intent i = getIntent();
        user = i.getParcelableExtra("user");

        WebSocket.bindGame(this);
        setDefaultValues();

        topLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(topLeft);
            }
        });

        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(topRight);
            }
        });

        bottomLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(bottomLeft);
            }
        });

        bottomRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(bottomRight);
            }
        });
    }

    public void nextQuestion(JSONObject jObject){
        if(!firstQuestion){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            firstQuestion = false;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Question questionFromJ;
                Gson gson = new Gson();
                Type listType = new TypeToken<Question>() {}.getType();
                try {
                    questionFromJ = gson.fromJson(jObject.get("question").toString(), listType);
                    rightAnswer = questionFromJ.getCorrect();
                    wrong = questionFromJ.getWrong();
                    question.setText(questionFromJ.getQuestion());

                    counter += 1;
                    round.setText(counter + " / " + 5);

                    List<String> questionList = new ArrayList<>();
                    setDefaultValues();
                    questionList.clear();
                    questionList.addAll(wrong);
                    questionList.add(rightAnswer);
                    setAnswersOnButtons(buttonList, questionList);
                    checkNumberOfAnsweres(wrong);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void endOfGame(JSONObject jObject){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Rank>>() {}.getType();
            ArrayList<Rank> rankList = gson.fromJson(jObject.get("ranking").toString(), listType);
            Intent intent = new Intent(this, GameFinishActivity.class);
            intent.putParcelableArrayListExtra("ranking", rankList);
            startActivityForResult(intent, 11);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDefaultValues(){
        timer.setText("");

        topLeft.setClickable(true);
        topRight.setClickable(true);
        bottomLeft.setClickable(true);
        bottomRight.setClickable(true);
        bottomLeft.setVisibility(View.VISIBLE);
        bottomRight.setVisibility(View.VISIBLE);

        setDimensionOfButton(buttonEnum.DEFAULT_BUTTONS.getHeight(), topLeft, false);
        setDimensionOfButton(buttonEnum.DEFAULT_BUTTONS.getHeight(), topRight, false);
        setDimensionOfButton(buttonEnum.DEFAULT_BUTTONS.getWidth(), bottomLeft, true);

        DrawableCompat.setTint(topLeft.getBackground(), getColor(R.color.white));
        DrawableCompat.setTint(topRight.getBackground(), getColor(R.color.white));
        DrawableCompat.setTint(bottomLeft.getBackground(), getColor(R.color.white));
        DrawableCompat.setTint(bottomRight.getBackground(), getColor(R.color.white));
    }

    private void checkNumberOfAnsweres(List<String> wrong){
        if(wrong.size() == 1){
            bottomLeft.setClickable(false);
            bottomRight.setClickable(false);
            bottomRight.setVisibility(View.INVISIBLE);
            bottomLeft.setVisibility(View.INVISIBLE);

            setDimensionOfButton(buttonEnum.TWO_BUTTONS.getHeight(), topLeft, false);
            setDimensionOfButton(buttonEnum.TWO_BUTTONS.getHeight(), topRight, false);
        }else if(wrong.size() == 2){
            bottomRight.setVisibility(View.INVISIBLE);
            bottomRight.setClickable(false);
            setDimensionOfButton(buttonEnum.THREE_BUTTONS.getWidth(), bottomLeft, true);
        }else{

        }
    }

    private void setDimensionOfButton(float dp, Button bt, boolean isWidth){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) bt.getLayoutParams();
        if(isWidth){
            params.width = pixels;
        }else{
            params.height = pixels;
        }
        bt.setLayoutParams(params);
    }

    private void setAnswersOnButtons(List<Button> buttonList, List<String> questionList){
        Random rand = new Random();
        int i=0;
        while(questionList.size() > 0){
            int randomNr = rand.nextInt(questionList.size());
            buttonList.get(i).setText(questionList.get(randomNr));
            questionList.remove(randomNr);
            i++;
        }
    }

    private void checkAnswer(Button bt){
        String text = bt.getText().toString();
        // set the colors
        if(rightAnswer.equals(text)){
            Drawable drawable = bt.getBackground();
            DrawableCompat.setTint(drawable, getColor(R.color.correct));
        }else{
            if(topRight.getText().toString().equals(rightAnswer)){
                Drawable drawable = topRight.getBackground();
                DrawableCompat.setTint(drawable, getColor(R.color.correct));
            }else if(topLeft.getText().toString().equals(rightAnswer)){
                Drawable drawable = topLeft.getBackground();
                DrawableCompat.setTint(drawable, getColor(R.color.correct));
            }else if(bottomRight.getText().toString().equals(rightAnswer)){
                Drawable drawable = bottomRight.getBackground();
                DrawableCompat.setTint(drawable, getColor(R.color.correct));
            }else if(bottomLeft.getText().toString().equals(rightAnswer)){
                Drawable drawable = bottomLeft.getBackground();
                DrawableCompat.setTint(drawable, getColor(R.color.correct));
            }
            Drawable drawable = bt.getBackground();
            DrawableCompat.setTint(drawable, getColor(R.color.wrong));
        }

        WebSocket.send(String.format("{\"action\":\"ANSWER\",\"question\":\"%d\",\"token\":\"%s\",\"answer\":\"%s\"}", counter-1, user.getToken(), bt.getText()));

        topLeft.setClickable(false);
        topRight.setClickable(false);
        bottomLeft.setClickable(false);
        bottomRight.setClickable(false);

        timer.setText("Waiting for other Players...");
        Intent intent = getIntent();
        int rounds = intent.getIntExtra("rounds", 0);
        if(counter <= rounds){
            round.setText(counter + " / " + rounds);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Weiterleiten
        Intent intent = new Intent();
        intent.putExtra("exit", data.getStringExtra("exit"));
        setResult(10, intent);
        finish();
    }
}
