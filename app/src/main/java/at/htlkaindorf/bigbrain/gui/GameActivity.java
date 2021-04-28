package at.htlkaindorf.bigbrain.gui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import at.htlkaindorf.bigbrain.bl.ButtonSize;
import at.htlkaindorf.bigbrain.bl.Game;
import at.htlkaindorf.bigbrain.R;

public class GameActivity extends AppCompatActivity {
    // Text
    private TextView question;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Text
        question = findViewById(R.id.tvQuestion);

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
        questionList.addAll(wrong);
        questionList.add(rightAnswer);
        setAnswersOnButtons(buttonList, questionList);
        if(wrong.size() == 1){
            bottomRight.setVisibility(View.INVISIBLE);
            bottomRight.setClickable(false);
            bottomLeft.setVisibility(View.INVISIBLE);
            bottomLeft.setClickable(false);
            setDimensionOfButton(buttonEnum.TWO_BUTTONS.getHeight(), topLeft, false);
            setDimensionOfButton(buttonEnum.TWO_BUTTONS.getHeight(), topRight, false);
        }else if(wrong.size() == 2){
            bottomRight.setVisibility(View.INVISIBLE);
            bottomRight.setClickable(false);
            setDimensionOfButton(buttonEnum.THREE_BUTTONS.getWidth(), bottomLeft, true);
        }else{

        }


        topLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(topLeft, rightAnswer);
            }
        });

        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(topRight, rightAnswer);
            }
        });

        bottomLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(bottomLeft, rightAnswer);
            }
        });

        bottomRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(bottomRight, rightAnswer);
            }
        });
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

    private void checkAnswer(Button bt, String rightAnswer){
        String text = bt.getText().toString();
        Drawable drawable = bt.getBackground();
        if(rightAnswer.equals(text)){
            DrawableCompat.setTint(drawable, getColor(R.color.correct));
        }else{
            DrawableCompat.setTint(drawable, getColor(R.color.wrong));
        }
        new CountDownTimer(3000, 1000){
            //TODO disable buttons
            @Override
            public void onTick(long l) {
                System.out.println("Time: " + l / 1000);
            }

            @Override
            public void onFinish() {
                
            }
        }.start();
    }
}
