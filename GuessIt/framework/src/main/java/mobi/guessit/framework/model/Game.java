package mobi.guessit.framework.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Game {

    private String name;

    @SerializedName("ad_unit_id")
    private String adUnitId;

    @SerializedName("analytics_tracking_id")
    private String analyticsTrackingId;

    private GameOptions options;

    @SerializedName("ui")
    private UserInterface userInterface;

    private List<Level> levels;

    public String getAdUnitId() {
        return adUnitId;
    }

    public void setAdUnitId(String adUnitId) {
        this.adUnitId = adUnitId;
    }

    public String getAnalyticsTrackingId() {
        return analyticsTrackingId;
    }

    public void setAnalyticsTrackingId(String analyticsTrackingId) {
        this.analyticsTrackingId = analyticsTrackingId;
    }

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

    public static Game fromJson(String json) {
        Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create();
        return gson.fromJson(json, Game.class);
    }

    public List<Level> getTodolevels() {
        Set<String> finishedLevels = Configuration.getInstance().getFinishedLevelNames();

        List<Level> levels = new LinkedList<Level>();
        for (Level level : getLevels()) {
            if (!finishedLevels.contains(level.getImageName())) {
                levels.add(level);
            }
        }

        return levels;
    }

    public List<Level> getFinishedLevels() {
        Set<String> finishedLevels = Configuration.getInstance().getFinishedLevelNames();

        List<Level> levels = new LinkedList<Level>();
        for (Level level : getLevels()) {
            if (finishedLevels.contains(level.getImageName())) {
                levels.add(level);
            }
        }

        return levels;
    }
}
