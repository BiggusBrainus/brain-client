package at.htlkaindorf.bigbrain.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Holder for the RankingAdapter
 * @version BigBrain v1
 * @since 11.06.2021
 * @author Nico Pessnegger
 */
public class RankingHolder extends RecyclerView.ViewHolder {
    private TextView username;
    private TextView score;
    private ConstraintLayout cl;

    public RankingHolder(@NonNull View itemView, TextView username, TextView score, ConstraintLayout cl) {
        super(itemView);
        this.username = username;
        this.score = score;
        this.cl = cl;
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

    public ConstraintLayout getCl() {
        return cl;
    }

    public void setCl(ConstraintLayout cl) {
        this.cl = cl;
    }
}
