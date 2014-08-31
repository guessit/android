package mobi.guessit.framework.view;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import mobi.guessit.framework.R;
import mobi.guessit.framework.helper.BackgroundHelper;
import mobi.guessit.framework.helper.ColorHelper;
import mobi.guessit.framework.model.Configuration;
import mobi.guessit.framework.model.Game;
import mobi.guessit.framework.model.UserInterfaceElement;

public class PlaceholderView extends RelativeLayout {

    public PlaceholderView(Context context) {
        super(context);
    }

    public PlaceholderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaceholderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setupUI();
    }

    private void setupUI() {
        Game game = Configuration.getInstance().getGame();

        if (game != null) {
            UserInterfaceElement placeholderUI = game.getUserInterface().getPlaceholder();

            PaintDrawable background = new PaintDrawable(ColorHelper.parseColor(
                placeholderUI.getBackgroundColor()));
            background.setCornerRadius(getResources().getDimension(
                R.dimen.level_placeholder_corner_radius));

            BackgroundHelper.getInstance().setBackground(this, background);
        }
    }

    private LetterButton letterButton;
    public LetterButton getLetterButton() {
        if (letterButton == null) {
            letterButton = (LetterButton) findViewById(R.id.letter_button);
        }
        return letterButton;
    }

    public void setLetter(String letter) {
        getLetterButton().setPlaceholder(letter);
    }

    public String getLetter() {
        String letter = null;

        if (getLetterButton().isPlacedOnAnswer()) {
            letter = getLetterButton().getLetter();
        }

        return letter;
    }

    public boolean canRemoveLetter() {
        return getLetterButton().isActive();
    }

    public boolean canDisplayLetter() {
        return getLetterButton().canDisplayLetter();
    }

    public void displayLetter(LetterButton letter) {
        getLetterButton().displayLetter(letter);
    }

    public void reset() {
        getLetterButton().setAlpha(0.f);
        getLetterButton().setOriginLetter(null);

        resetAlphaIfButtonIsAnimating();
    }

    /**
     * For some reason the getAnimation is not returning an animation,
     * it happens when the user types too fast and finish guessing an anwser.
     *
     * If we do not reset the alpha, the letter continues showing on the
     * answer placeholder, and if the uses clicks on it the app crashes.
     *
     * Dont know if this overhead can slow down the app on slow devices.
     *
     * 300 ms is the default animation duration, so it is the last postDelayed call
     */
    private void resetAlphaIfButtonIsAnimating() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (getLetterButton().getAlpha() != 0) {
                    getLetterButton().setAlpha(0);
                }
            }
        };

        new Handler().postDelayed(runnable, 50);
        new Handler().postDelayed(runnable, 100);
        new Handler().postDelayed(runnable, 150);
        new Handler().postDelayed(runnable, 200);
        new Handler().postDelayed(runnable, 250);
        new Handler().postDelayed(runnable, 300);
    }
}
