package mobi.guessit.framework.activity.adapter;

public class Settings {

    private SettingsType type;
    private int resourceId;

    public Settings(SettingsType type, int resourceId) {
        this.type = type;
        this.resourceId = resourceId;
    }

    public SettingsType getType() {
        return type;
    }

    public void setType(SettingsType type) {
        this.type = type;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
