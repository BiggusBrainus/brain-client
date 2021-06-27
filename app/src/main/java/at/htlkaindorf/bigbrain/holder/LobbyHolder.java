package at.htlkaindorf.bigbrain.holder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Holder for the AllLobbiesAdapter
 * @version BigBrain v1
 * @since 26.05.2021
 * @author Nico Pessnegger
 */
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
