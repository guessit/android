package mobi.guessit.framework.helper;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewHelper {

    public static List<View> getViewsWithTag(ViewGroup root, Object tag) {
        List<View> views = new ArrayList<View>();

        int noChild = root.getChildCount();
        for (int i = 0; i < noChild; i++) {
            View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsWithTag((ViewGroup) child, tag));
            }

            if (tag.equals(child.getTag())) {
                views.add(child);
            }
        }

        return views;
    }
}
