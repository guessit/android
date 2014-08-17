package mobi.guessit.guessit.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BumpAnimator;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.helper.RandomLetterHelper;
import mobi.guessit.guessit.helper.ViewHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.Level;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class KeypadView extends LinearLayout {

    private Level level;
    private OnKeypadListener onKeypadListener;

    public KeypadView(Context context) {
        super(context);
    }

    public KeypadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeypadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setupUI();
    }

    public void setLevel(Level level) {
        this.level = level;

        this.setupKeypad(level);
    }

    public OnKeypadListener getOnKeypadListener() {
        return onKeypadListener;
    }

    public void setOnKeypadListener(OnKeypadListener onKeypadListener) {
        this.onKeypadListener = onKeypadListener;
    }

    private void setupUI() {
        Game game = Configuration.getInstance().getGame();

        if (game != null) {
            UserInterfaceElement keypadUI = game.getUserInterface().getKeypad();

            View keypadView = findViewById(R.id.level_keypad_view);
            keypadView.setBackgroundColor(ColorHelper.parseColor(keypadUI.getBackgroundColor()));

            createKeypadRows(game);
            initializeActionButton(game);
        }
    }

    private void createKeypadRows(Game game) {
        LinearLayout lettersContainer = (LinearLayout) findViewById(
            R.id.level_keypad_letters_container);

        int rows = game.getOptions().getKeypadRows();
        int columns = game.getOptions().getKeypadColumns();

        for (int i = 0; i < rows; i++) {
            boolean isFirstRow = i == 0;
            boolean isLastRow = i == rows - 1;

            createEachKeypadRow(lettersContainer, columns, isFirstRow, isLastRow);
        }
    }

    private void createEachKeypadRow(LinearLayout keypadPlaceholder, int columns,
                                     boolean isFirstRow, boolean isLastRow) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);

        LayoutParams layoutParams = new LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

        if (isFirstRow) {
            layoutParams.setMargins(0, 0, 0, 2);
        } else if (isLastRow) {
            layoutParams.setMargins(0, 2, 0, 0);
        } else {
            layoutParams.setMargins(0, 2, 0, 2);
        }

        keypadPlaceholder.addView(layout, layoutParams);

        for (int j = 0; j < columns; j++) {
            boolean isFirstColumn = j == 0;
            boolean isLastColumn = j == columns - 1;

            createEachRowLetterButton(layout, isFirstColumn, isLastColumn);
        }
    }

    private void createEachRowLetterButton(LinearLayout layout,
                                           boolean isFirstColumn,
                                           boolean isLastColumn) {
        LetterButton button = new LetterButton(getContext());

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LetterButton letter = (LetterButton) view;
                if (letter.isActive()) {
                    if (getOnKeypadListener().canAddLetter(KeypadView.this, letter)) {
                        getOnKeypadListener().onLetterAdded(KeypadView.this, letter);
                        BumpAnimator.getInstance().animateOut(view);
                    }
                }
            }
        });

        LayoutParams buttonParams = new LayoutParams(
            0, ViewGroup.LayoutParams.MATCH_PARENT, 1);

        if (isFirstColumn) {
            buttonParams.setMargins(0, 0, 2, 0);
        } else if (isLastColumn) {
            buttonParams.setMargins(2, 0, 0, 0);
        } else {
            buttonParams.setMargins(2, 0, 2, 0);
        }

        layout.addView(button, buttonParams);
    }

    private void initializeActionButton(Game game) {
        Button helpButton = (Button) findViewById(R.id.level_keypad_action_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnKeypadListener().onHelpRequested(KeypadView.this, (Button) view);
            }
        });

        UserInterfaceElement actionUI = game.getUserInterface().getAction();

        helpButton.setBackgroundColor(ColorHelper.parseColor(actionUI.getBackgroundColor()));
        helpButton.setShadowLayer(1, 0, -1,
            ColorHelper.parseColor(actionUI.getShadowColor()));

        ColorStateList colors = new ColorStateList(new int[][]{
            new int[]{android.R.attr.state_pressed},
            StateSet.WILD_CARD
        }, new int[]{
            ColorHelper.parseColor(actionUI.getSecondaryTextColor()),
            ColorHelper.parseColor(actionUI.getTextColor()),
        });

        helpButton.setTextColor(colors);
    }

    private void setupKeypad(Level level) {
        ViewGroup keypad = (ViewGroup) findViewById(R.id.level_keypad_view);
        List<View> keys = ViewHelper.getViewsWithTag(keypad, "key_button");

        final int answerLength = level.getNoSpacesAnswer().length();

        List<String> letters = new ArrayList<String>();
        for (int i = 0; i < answerLength; i++) {
            letters.add(level.getLetterAt(i));
        }

        int missingLetterCount = keys.size() - answerLength;
        for (int i = 0; i < missingLetterCount; i++) {
            boolean isEven = i % 2 == 0;
            if (isEven) {
                letters.add(RandomLetterHelper.getInstance().randomVowel());
            } else {
                letters.add(RandomLetterHelper.getInstance().randomConsonant());
            }
        }

        Collections.shuffle(letters);

        for (int i = 0; i < keys.size(); i++) {
            LetterButton key = (LetterButton) keys.get(i);
            key.setLetter(letters.get(i));
        }
    }

    public void recoverLetter(LetterButton letter) {
        BumpAnimator.getInstance().animateIn(letter.getOriginLetter());
        BumpAnimator.getInstance().animateOut(letter);
        letter.setOriginLetter(null);
    }

    public interface OnKeypadListener {
        boolean canAddLetter(KeypadView keypad, LetterButton letter);
        void onLetterAdded(KeypadView keypad, LetterButton letter);
        void onHelpRequested(KeypadView keypad, Button helpButton);
    }
}
