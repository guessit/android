package mobi.guessit.guessit.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {

    private String name;

    @SerializedName("ad_mediation_id")
    private String adMediationId;

    @SerializedName("analytics_tracking_id")
    private String analyticsTrackingId;

    private GameOptions options;

    @SerializedName("ui")
    private UserInterface userInterface;

    private List<Level> levels;

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public GameOptions getOptions() {
        return options;
    }

    public void setOptions(GameOptions options) {
        this.options = options;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public List<Level> getTodoLevels() {
        List<Level> todoLevels = new LinkedList<Level>();

        for (Level level : getLevels()) {
            if (!level.isFinished()) todoLevels.add(level);
        }

        return todoLevels;
    }

    public Level getNextLevel() {
        Level nextLevel;

        List<Level> todoLevels = getTodoLevels();
        if (todoLevels.size() > 0) {
            if (getOptions().isRandomize()) {
                int randomIndex = new Random().nextInt(getLevels().size());
                nextLevel = getTodoLevels().get(randomIndex);
            } else {
                nextLevel = getTodoLevels().get(0);
            }
        } else {
            nextLevel = Level.guessItLevel();
        }

        return nextLevel;
    }

    public static Game fromJson(String json) {
        Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create();
        return gson.fromJson(json, Game.class);
    }
}
