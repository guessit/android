package mobi.guessit.guessit.helper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class BumpAnimator {

    private static BumpAnimator instance;
    public static BumpAnimator getInstance() {
        if (instance == null) instance = new BumpAnimator();
        return instance;
    }

    private BumpAnimator() {
    }

    private void animate(View view, boolean in) {
        float origin = in ? 0.f : 1.f;
        float destination = in ? 1.f : 0.f;

        view.setScaleX(origin);
        view.setScaleY(origin);
        view.setAlpha(origin);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", destination);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", destination);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", destination);

        AnimatorSet bumpScale = new AnimatorSet();
        bumpScale.setInterpolator(new OvershootInterpolator());
        bumpScale.play(scaleX).with(scaleY).with(alpha);
        bumpScale.start();
    }

    public void animateIn(View view) {
        animate(view, true);
    }

    public void animateOut(View view) {
        animate(view, false);
    }
}
