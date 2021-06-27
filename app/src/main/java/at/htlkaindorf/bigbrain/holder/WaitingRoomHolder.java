package at.htlkaindorf.bigbrain.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Holder for the WaitingRoomAdapter
 * @version BigBrain v1
 * @since 02.06.2021
 * @author Nico Pessnegger
 */
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
