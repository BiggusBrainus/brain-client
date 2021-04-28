package at.htlkaindorf.bigbrain.bl;

import android.app.Activity;
import android.widget.Button;

import java.util.List;
import java.util.Random;

import at.htlkaindorf.bigbrain.R;

public class Game {
    public void setAnswerOnButtons(String correct, List<String> wrong, List<Button> buttons){
        List<String> allAnswers = null;
        allAnswers.addAll(wrong);
        allAnswers.add(correct);

        int i = 0;
        Random rand = new Random();
        while (allAnswers.size() < 0){
            int random = rand.nextInt(allAnswers.size()-1);
            buttons.get(i).setText(allAnswers.get(random));
            i++;
        }
    }

    public void checkAnswer(String answer, String correct, Button button, Activity activity){
        if(answer.equals(correct)){
            button.setBackgroundColor(activity.getResources().getColor(R.color.correct));
        }else{
            button.setBackgroundColor(activity.getResources().getColor(R.color.wrong));
        }
    }
}
