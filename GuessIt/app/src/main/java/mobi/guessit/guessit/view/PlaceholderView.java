package mobi.guessit.guessit.view;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BackgroundHelper;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
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

        UserInterfaceElement placeholderUI = Configuration.getInstance().getGame().
            getUserInterface().getPlaceholder();

        PaintDrawable background = new PaintDrawable(ColorHelper.parseColor(
            placeholderUI.getBackgroundColor()));
        background.setCornerRadius(getResources().getDimension(
            R.dimen.level_placeholder_corner_radius));

        BackgroundHelper.getInstance().setBackground(this, background);
    }

    private LetterButton button;
    private LetterButton getButton() {
        if (button == null) {
            button = (LetterButton) findViewById(R.id.letter_button);
        }
        return button;
    }

    public void setLetter(String letter) {
        getButton().setLetter(letter);
    }
}
