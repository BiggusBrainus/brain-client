package at.htlkaindorf.bigbrain.beans;

import java.util.List;

public class Question {
    private String question;
    private String correct;
    private List<String> wrong;

    public Question(String question, String correct, List<String> wrong) {
        this.question = question;
        this.correct = correct;
        this.wrong = wrong;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public List<String> getWrong() {
        return wrong;
    }

    public void setWrong(List<String> wrong) {
        this.wrong = wrong;
    }
}
