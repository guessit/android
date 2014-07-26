package mobi.guessit.guessit.model;

public class Configuration {

    private static Configuration _instance;
    private Game game;

    private Configuration() {
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
}
