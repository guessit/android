package mobi.guessit.guessit.view;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BackgroundHelper;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.UserInterfaceElement;

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
    }
}
