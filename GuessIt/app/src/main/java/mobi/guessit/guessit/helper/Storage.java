package mobi.guessit.guessit.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by marlonandrade on 8/19/14.
 */
public class Storage {

    public static final String PREFS_NAME = "conf";
    private Context context;

    private static Storage instance;
    public static Storage getInstance() {
        if (instance == null) instance = new Storage();
        return instance;
    }

    private Storage() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private SharedPreferences getPreferences() {
        return getContext().getSharedPreferences(PREFS_NAME, 0);
    }

    public boolean getBoolean(String key) {
        return getPreferences().getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value) {
        getPreferences().edit().putBoolean(key, value).commit();
    }

    public int getInt(String key) {
        return getPreferences().getInt(key, 0);
    }

    public void setInt(String key, int value) {
        getPreferences().edit().putInt(key, value).commit();
    }

    public String getString(String key) {
        return getPreferences().getString(key, "");
    }

    public void setString(String key, String value) {
        getPreferences().edit().putString(key, value).commit();
    }

    public Set<String> getStringSet(String key) {
        return getPreferences().getStringSet(key, new HashSet<String>());
    }

    public void setStringSet(String key, Set<String> value) {
        getPreferences().edit().putStringSet(key, value).commit();
    }
}
