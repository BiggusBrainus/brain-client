package at.htlkaindorf.bigbrain.beans;

import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import at.htlkaindorf.bigbrain.adapter.WaitingRoomAdapter;
import at.htlkaindorf.bigbrain.gui.GameActivity;
import at.htlkaindorf.bigbrain.gui.MainActivity;
import at.htlkaindorf.bigbrain.gui.WaitingRoomActivity;
import tech.gusavila92.websocketclient.WebSocketClient;

/**
 * Used for all websocket operations
 * @version BigBrain v1
 * @since 09.06.2021
 * @author Nico Pessnegger
 */
public class WebSocket {
    private static WebSocketClient webSocketClient;
    public static WaitingRoomActivity wr;
    public static GameActivity ga;
    public static MainActivity ma;

    public static void bindWaitingRoom(WaitingRoomActivity wait){
        wr = wait;
    }

    public static void bindGame(GameActivity game){
        ga = game;
    }

    public static void bindMain(MainActivity main){
        ma = main;
    }

    // To create a new WebSocket
    public static void createWebSocketClient() {
        URI uri;
        try {
            uri = new URI("wss://brain.b34nb01z.club/ws/game");
        }
        catch (URISyntaxException e) {
            Log.i("Exception", "No valid URI in WebSocket");
            return;
        }

        webSocketClient = new tech.gusavila92.websocketclient.WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
            }
            @Override
            public void onTextReceived(String s) {
                final String message = s;
                try{
                    JSONObject jObject = new JSONObject(message);
                    switch (jObject.get("action").toString()){
                        // New player joined lobby
                        case "LOBBY_PLAYERS_UPDATE":
                            if(wr != null){
                                wr.updatePlayer(jObject);
                            }else if(ma != null){
                                ma.startGameActivity();
                            }
                            break;
                        // A user pressed the start button in WaitingRoomActivity
                        case "START_GAME":
                            if(wr != null){
                                wr.startGame();
                            }
                            break;
                        // All players answered a question and now get the next one
                        case "NEXT_QUESTION":
                            while(ga == null){
                                Thread.sleep(10);
                            }
                            ga.nextQuestion(jObject);
                            break;
                        // All questions have been answered --> games ends
                        case "END_OF_GAME":
                            ga.endOfGame(jObject);
                            ga = null;
                            wr = null;
                            ma = null;
                            break;
                    }
                } catch (Exception e){
                    Log.i("Exception", "Error occurred in WebSocket");
                }
            }

            @Override
            public void onBinaryReceived(byte[] data) {

            }

            @Override
            public void onPingReceived(byte[] data) {

            }

            @Override
            public void onPongReceived(byte[] data) {

            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                System.out.println("onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    public static void send(String str){
        webSocketClient.send(str);
    }
}
