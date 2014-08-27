package mobi.guessit.framework.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ResourceHelper {

    private static ResourceHelper instance;
    public static ResourceHelper getInstance() {
        if (instance == null) instance = new ResourceHelper();
        return instance;
    }

    private ResourceHelper() {}

    public Drawable getImage(Context context, String fileName) {
        int id = getResourceId(context, fileName, "drawable");
        return context.getResources().getDrawable(id);
    }

    public String getString(Context context, String key) {
        int id = getResourceId(context, key, "string");
        return context.getResources().getString(id);
    }

    private int getResourceId(Context context, String name, String type) {
        return context.getResources().getIdentifier(
            name, type, context.getPackageName());
    }
}
