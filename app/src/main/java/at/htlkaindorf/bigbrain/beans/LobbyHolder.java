package at.htlkaindorf.bigbrain.beans;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LobbyHolder extends RecyclerView.ViewHolder{
    private TextView lobbyName;
    private TextView amountOfPlayers;

    public LobbyHolder(@NonNull View itemView, TextView lobbyName, TextView amountOfPlayers) {
        super(itemView);
        this.lobbyName = lobbyName;
        this.amountOfPlayers = amountOfPlayers;
    }

    public TextView getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(TextView lobbyName) {
        this.lobbyName = lobbyName;
    }

    public TextView getAmountOfPlayers() {
        return amountOfPlayers;
    }

    public void setAmountOfPlayers(TextView amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
    }
}
