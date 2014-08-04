package mobi.guessit.guessit.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Game {

    private String name;

    @SerializedName("ad_mediation_id")
    private String adMediationId;

    @SerializedName("analytics_tracking_id")
    private String analyticsTrackingId;

    private GameOptions options;

    @SerializedName("ui")
    private UserInterface userInterface;

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

    public static Game fromJson(String json) {
        return new Gson().fromJson(json, Game.class);
    }
}
