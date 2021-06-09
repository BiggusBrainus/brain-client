package at.htlkaindorf.bigbrain.beans;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WaitingRoomHolder extends RecyclerView.ViewHolder {
    private TextView playername;


    public WaitingRoomHolder(@NonNull View itemView, TextView playername) {
        super(itemView);
        this.playername = playername;
    }

    public TextView getPlayername() {
        return playername;
    }

    public void setPlayername(TextView playername) {
        this.playername = playername;
    }
}
