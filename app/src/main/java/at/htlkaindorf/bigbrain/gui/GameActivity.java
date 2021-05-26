package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

        // Set text to test that right answers become green and wrong answers become red
        String rightAnswer = "Right";
        List<String> wrong = Arrays.asList("Wrong", "Wrong", "Wrong");

        List<Button> buttonList = new ArrayList<>();
        buttonList.add(topLeft);
        buttonList.add(topRight);
        buttonList.add(bottomLeft);
        buttonList.add(bottomRight);
        List<String> questionList = new ArrayList<>();

        Intent intent = getIntent();
        int rounds = intent.getIntExtra("rounds", 0);
        counter += 1;
        round.setText(counter + " / " + rounds);
        setDefaultValues();
        questionList.clear();
        questionList.addAll(wrong);
        questionList.add(rightAnswer);
        setAnswersOnButtons(buttonList, questionList);
        checkNumberOfAnsweres(wrong);


        topLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(topLeft, rightAnswer, questionList,  wrong, buttonList);
            }
        });

        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(topRight, rightAnswer, questionList,  wrong, buttonList);
            }
        });

        bottomLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(bottomLeft, rightAnswer, questionList,  wrong, buttonList);
            }
        });

        bottomRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(bottomRight, rightAnswer, questionList,  wrong, buttonList);
            }
        });
    }

    private void setDefaultValues(){
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

    private void checkAnswer(Button bt, String rightAnswer, List<String> questionList, List<String> wrong, List<Button> buttonList){
        String text = bt.getText().toString();
        Drawable drawable = bt.getBackground();
        if(rightAnswer.equals(text)){
            DrawableCompat.setTint(drawable, getColor(R.color.correct));
        }else{
            DrawableCompat.setTint(drawable, getColor(R.color.wrong));
        }

        topLeft.setClickable(false);
        topRight.setClickable(false);
        bottomLeft.setClickable(false);
        bottomRight.setClickable(false);
        new CountDownTimer(3000, 1000){
            @Override
            public void onTick(long l) {
                timer.setText(l / 1000 + " s");
            }

            @Override
            public void onFinish() {
                timer.setText("");
                counter += 1;
                Intent intent = getIntent();
                int rounds = intent.getIntExtra("rounds", 0);
                round.setText(counter + " / " + rounds);
                if(counter <= rounds){
                    //wrong.remove(wrong.size()-1);
                    setDefaultValues();
                    questionList.clear();
                    questionList.addAll(wrong);
                    questionList.add(rightAnswer);
                    setAnswersOnButtons(buttonList, questionList);
                    checkNumberOfAnsweres(wrong);
                }else{
                    finish();
                }

            }
        }.start();
    }
}
