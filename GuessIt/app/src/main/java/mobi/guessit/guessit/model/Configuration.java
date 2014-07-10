package mobi.guessit.guessit.model;

import org.json.JSONObject;

/**
 * Created by marlonandrade on 7/9/14.
 */
public class Configuration {

    private static Configuration _instance;
    private Game game;

    private Configuration() {
        loadGameFromJsonFile();
    }

    public static Configuration getInstance() {
        if (_instance == null) _instance = new Configuration();
        return _instance;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void loadGameFromJsonFile() {
        this.game = new Game();

        JSONObject json = new JSONObject();
    }
}
