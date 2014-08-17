package mobi.guessit.guessit.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.widget.Button;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BackgroundHelper;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class LetterButton extends Button {

    private UserInterfaceElement letterUI;

    public LetterButton(Context context) {
        super(context);
        this.initializeView(context);
    }

    public LetterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initializeView(context);
    }

    public LetterButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initializeView(context);
    }

    private void initializeView(Context context) {
        this.setTag("key_button");
        this.setTextSize(getResources().getDimension(R.dimen.level_letter_text_size));
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
            "fonts/Avenir Next Condensed-Medium.ttf"), Typeface.NORMAL);
    }

    public void setLetterUI(UserInterfaceElement letterUI) {
        this.letterUI = letterUI;

        BackgroundHelper.getInstance().setBackground(this, backgroundColor(letterUI));
        this.setTextColor(buttonTextColor(letterUI));
        this.setShadowLayer(1, 0, -1, ColorHelper.parseColor(letterUI.getShadowColor()));
    }

    private Drawable backgroundColor(UserInterfaceElement ui) {
        int[] pressedState = new int[]{android.R.attr.state_pressed};

        StateListDrawable backgroundColor = new StateListDrawable();
        backgroundColor.addState(pressedState, new ColorDrawable(
            ColorHelper.parseColor(ui.getSecondaryBackgroundColor())
        ));
        backgroundColor.addState(StateSet.WILD_CARD, new ColorDrawable(
            ColorHelper.parseColor(ui.getBackgroundColor())
        ));

        return backgroundColor;
    }

    private ColorStateList buttonTextColor(UserInterfaceElement ui) {
        int[] pressedState = new int[]{android.R.attr.state_pressed};

        ColorStateList buttonTextColor = new ColorStateList(new int[][]{
            pressedState,
            StateSet.WILD_CARD
        }, new int[]{
            ColorHelper.parseColor(ui.getSecondaryTextColor()),
            ColorHelper.parseColor(ui.getTextColor())
        });

        return buttonTextColor;
    }
}
