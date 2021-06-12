package at.htlkaindorf.bigbrain.beans;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RankingHolder extends RecyclerView.ViewHolder {
    private TextView username;
    private TextView score;

    public RankingHolder(@NonNull View itemView, TextView username, TextView score) {
        super(itemView);
        this.username = username;
        this.score = score;
    }

    public TextView getUsername() {
        return username;
    }

    public void setUsername(TextView username) {
        this.username = username;
    }

    public TextView getScore() {
        return score;
    }

    public void setScore(TextView score) {
        this.score = score;
    }
}
