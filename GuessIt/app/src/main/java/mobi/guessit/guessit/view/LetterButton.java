package mobi.guessit.guessit.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BackgroundHelper;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class LetterButton extends Button {

    public LetterButton(Context context) {
        super(context);
        setupUI();
    }

    public LetterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setupUI();
    }

    private void setupUI() {
        setTag("key_button");
        setTextSize(getResources().getDimension(R.dimen.level_letter_text_size));
        setTypeface(Typeface.createFromAsset(getContext().getAssets(),
            "fonts/Avenir Next Condensed-Medium.ttf"), Typeface.NORMAL);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = ((Button) view).getText();
                Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        Game game = Configuration.getInstance().getGame();

        if (game != null) {
            UserInterfaceElement letterUI = game.getUserInterface().getLetter();

            BackgroundHelper.getInstance().setBackground(this, backgroundColor(letterUI));
            setTextColor(buttonTextColor(letterUI));
            setShadowLayer(1, 0, -1, ColorHelper.parseColor(letterUI.getShadowColor()));
        }
    }

    public void setLetter(String letter) {
        setText(letter);
    }

    public void setPlaceholder(String letter) {
        setLetter(letter);
        setAlpha(0.f);
        setScaleX(0.01f);
        setScaleY(0.01f);
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
