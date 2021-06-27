package at.htlkaindorf.bigbrain.beans;

import java.util.List;

/**
 * Represents a Lobby
 * @version BigBrain v1
 * @since 26.05.2021
 * @author Nico Pessnegger
 */
public class Lobby {
    private String name;
    private List<User> players;
    private List<Category> categories;
    private boolean isInGame;

    public Lobby(String name, List<User> players, List<Category> categories, boolean isInGame){
        this.name = name;
        this.players = players;
        this.categories = categories;
        this.isInGame = isInGame;
    }

    public Lobby() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }
}
