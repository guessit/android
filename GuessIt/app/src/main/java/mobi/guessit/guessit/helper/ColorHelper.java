package mobi.guessit.guessit.helper;

import android.graphics.Color;

public class ColorHelper {
    public static int parseColor(String rgba) {
        String argb = null;

        if (rgba.length() == 9) {
            argb = "#" + rgba.substring(7) + rgba.substring(1, 7);
        } else {
            argb = rgba;
        }

        return Color.parseColor(argb);
    }
}
