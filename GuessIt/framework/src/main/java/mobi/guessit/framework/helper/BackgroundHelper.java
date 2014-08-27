package mobi.guessit.framework.helper;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

public class BackgroundHelper {
    private static BackgroundHelper instance;

    private BackgroundHelper() {}

    public static BackgroundHelper getInstance() {
        if (instance == null) instance = new BackgroundHelper();
        return instance;
    }

    public void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
