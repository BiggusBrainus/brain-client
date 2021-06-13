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
import at.htlkaindorf.bigbrain.gui.WaitingRoomActivity;
import tech.gusavila92.websocketclient.WebSocketClient;

public class WebSocket {
    private static WebSocketClient webSocketClient;
    public static WaitingRoomActivity wr;
    public static GameActivity ga;

    public static void bindWaitingRoom(WaitingRoomActivity wait){
        wr = wait;
    }

    public static void bindGame(GameActivity game){
        ga = game;
    }

    // Alles für die Websockets
    public static void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("wss://brain.b34nb01z.club/ws/game");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new tech.gusavila92.websocketclient.WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
            }
            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received");
                final String message = s;
                try{
                    JSONObject jObject = new JSONObject(message);
                    Log.i("Websocket says: ", message);
                    switch (jObject.get("action").toString()){
                        case "LOBBY_PLAYERS_UPDATE":
                            Log.i("asdf", jObject.get("players").toString());
                            wr.updatePlayer(jObject);
                            break;
                        case "START_GAME":
                            wr.startGame();
                            break;
                        case "NEXT_QUESTION":
                            while(ga == null){
                                Thread.sleep(10);
                            }
                            ga.nextQuestion(jObject);
                            break;
                        case "END_OF_GAME":
                            ga.endOfGame(jObject);
                            ga = null;
                            break;
                    }
                } catch (Exception e){
                    e.printStackTrace();
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
                Log.i("WebSocket", "Closed ");
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
