package mobi.guessit.guessit.view;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BackgroundHelper;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class PlaceholderView extends RelativeLayout {

    private String letter;
    private UserInterfaceElement placeholderUI;

    public PlaceholderView(Context context) {
        super(context);
    }

    public PlaceholderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaceholderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private LetterButton button;
    private LetterButton getButton() {
        if (button == null) {
            button = (LetterButton) this.findViewById(R.id.letter_button);
        }
        return button;
    }

    public void setLetter(String letter) {
        this.letter = letter;

        this.adjustChildViews();
    }

    private void adjustChildViews() {
        this.getButton().setText(this.letter);
        this.getButton().setAlpha(0.f);
    }

    public void setPlaceholderUI(UserInterfaceElement placeholderUI) {
        this.placeholderUI = placeholderUI;

        PaintDrawable background = new PaintDrawable(ColorHelper.parseColor(
            this.placeholderUI.getBackgroundColor()));
        background.setCornerRadius(3.f);

        BackgroundHelper.getInstance().setBackground(this, background);
    }

    public void setLetterUI(UserInterfaceElement letterUI) {
        this.getButton().setLetterUI(letterUI);
    }
}
