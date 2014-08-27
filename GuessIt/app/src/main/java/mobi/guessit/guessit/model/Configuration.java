package mobi.guessit.guessit.model;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;
import java.util.Random;
import java.util.Set;

import mobi.guessit.guessit.helper.Storage;

public class Configuration {

    private static final String SHOW_ADS = "key_show_ads";
    private static final String NUMBER_HELP_REQUESTED = "key_no_help_requested";
    private static final String NUMBER_LEVEL_PRESENTED = "key_no_level_presented";
    private static final String FINISHED_LEVELS = "key_finished_levels";
    private static final String CURRENT_LEVEL = "key_current_level";

    public class Events {
        public static final String HELP_CATEGORY = "help";
        public static final String GAME_CATEGORY = "game";

        public static final String PLACE_CORRECT_LETTER = "place_correct_letter";
        public static final String ELIMINATE_WRONG_LETTER = "eliminate_wrong_letter";
        public static final String SKIP_LEVEL = "skip_level";

        public static final String LEVEL_LOADED = "level_loaded";
        public static final String LEVEL_CORRECT = "level_correct";
        public static final String HELP_REQUESTED = "help_requested";
        public static final String GAME_OVER = "game_over";
        public static final String RESET_PROGRESS = "reset_progress";
        public static final String AD_SHOWN = "ad_shown";
    }

    public class Views {
        public static final String MAIN_VIEW = "MainView";
        public static final String LEVEL_VIEW = "LevelView";
        public static final String SETTINGS_VIEW = "SettingsView";
    }

    private static Configuration _instance;
    private Game game;

    private Configuration() {
    }

    public static Configuration getInstance() {
        if (_instance == null) _instance = new Configuration();
        return _instance;
    }

    private Tracker tracker;

    public void initializeTracker(Context context) {
        tracker = GoogleAnalytics.getInstance(context).newTracker(getGame().getAnalyticsTrackingId());
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Level getLastLevel() {
        return Level.guessItLevel();
    }

    public String getCurrentLevelName() {
        String currentLevelName = Storage.getInstance().getString(CURRENT_LEVEL);

        if (currentLevelName != null) {
            Set<String> finishedLevelNames = getFinishedLevelNames();
            if (finishedLevelNames.contains(currentLevelName)) {
                currentLevelName = "";
            }
        }

        return currentLevelName;
    }

    public Level getCurrentLevel() {
        Level currentLevel = null;

        String currentLevelName = getCurrentLevelName();
        if (currentLevelName.equals("")) {
            currentLevel = getNextLevel();
        } else if (currentLevelName.equals("guessit_final")) {
            currentLevel = getLastLevel();
        } else {
            List<Level> todolevels = getGame().getTodolevels();
            for (Level level : todolevels) {
                if (level.getImageName().equals(currentLevelName)) {
                    currentLevel = level;
                    break;
                }
            }
        }

        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        Storage.getInstance().setString(CURRENT_LEVEL, currentLevel.getImageName());

        if (currentLevel != null) {
            incrementNumberOfLevelsPresented();
        }
    }

    public Level getNextLevel() {
        Level nextLevel;

        List<Level> todoLevels = getGame().getTodolevels();
        if (todoLevels.size() > 0) {
            if (!getCurrentLevelName().equals("")) {
                todoLevels.remove(getCurrentLevel());
            }

            if (getGame().getOptions().isRandomize()) {
                int randomIndex = new Random().nextInt(todoLevels.size());
                nextLevel = todoLevels.get(randomIndex);
            } else {
                nextLevel = todoLevels.get(0);
            }
        } else {
            nextLevel = Level.guessItLevel();
        }

        setCurrentLevel(nextLevel);

        return nextLevel;
    }

    public Set<String> getFinishedLevelNames() {
        return Storage.getInstance().getStringSet(FINISHED_LEVELS);
    }

    public int getNumberOfLevelsPresented() {
        return Storage.getInstance().getInt(NUMBER_LEVEL_PRESENTED);
    }

    private void setNumberOfLevelsPresented(int numberOfLevels) {
        Storage.getInstance().setInt(NUMBER_LEVEL_PRESENTED, numberOfLevels);
    }

    public void incrementNumberOfLevelsPresented() {
        setNumberOfLevelsPresented(getNumberOfLevelsPresented() + 1);
    }

    public int getNumberOfHelpRequested() {
        return Storage.getInstance().getInt(NUMBER_HELP_REQUESTED);
    }

    private void setNumberOfHelpRequested(int numberOfHelp) {
        Storage.getInstance().setInt(NUMBER_HELP_REQUESTED, numberOfHelp);
    }

    public void incrementNumberOfHelpRequested() {
        setNumberOfHelpRequested(getNumberOfHelpRequested() + 1);
    }

    public boolean isTimeToShowAd() {
        int levelsToShowAd = (int) (getGame().getLevels().size() / 10.f);
        int levelsPlusHelps = getNumberOfLevelsPresented() + getNumberOfHelpRequested();

        return levelsPlusHelps >= levelsToShowAd && hasMoreLevels();
    }

    public void resetCountersAfterShowingAd() {
        setNumberOfLevelsPresented(0);
        setNumberOfHelpRequested(0);
    }

    public boolean showAds() {
        int value = Storage.getInstance().getInt(SHOW_ADS);
        return value != 42;
    }

    public void setShowAds(boolean showAds) {
        int value = showAds ? 0 : 42;
        Storage.getInstance().setInt(SHOW_ADS, value);
    }

    public boolean hasMoreLevels() {
        List<Level> todoLevels = getGame().getTodolevels();
        return todoLevels.size() > 1;
    }

    public void setContext(Context applicationContext) {
        Storage.getInstance().setContext(applicationContext);
    }

    public void resetProgress() {
        Storage.getInstance().setStringSet(FINISHED_LEVELS, null);
        Storage.getInstance().setString(CURRENT_LEVEL, null);
    }

    public void markLevelFinished(Level level) {
        Set<String> levels = getFinishedLevelNames();
        levels.add(level.getImageName());
        Storage.getInstance().setStringSet(FINISHED_LEVELS, levels);
    }

    public void trackEvent(String category, String action, String label) {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build()
        );
    }

    public void trackView(String name) {
        tracker.setScreenName(name);
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }
}

