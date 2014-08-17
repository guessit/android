package mobi.guessit.guessit.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.helper.RandomLetterHelper;
import mobi.guessit.guessit.helper.ViewHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.Level;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class InputView extends LinearLayout {

    private Level level;

    public InputView(Context context) {
        super(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLevel(Level level) {
        this.level = level;

        getAnswerView().setAnswer(level.getAnswer());
        setupKeypad(level);
    }

    private AnswerView answerView;
    private AnswerView getAnswerView() {
        if (answerView == null) {
            answerView = (AnswerView) findViewById(R.id.level_answer_view);
        }
        return answerView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initializeKeypad();
        initializeActionButton();
    }

    private void initializeKeypad() {
        Game game = Configuration.getInstance().getGame();

        if (game != null) {
            UserInterfaceElement keypadUI = game.getUserInterface().getKeypad();

            View keypadView = findViewById(R.id.level_keypad);
            keypadView.setBackgroundColor(ColorHelper.parseColor(keypadUI.getBackgroundColor()));

            LinearLayout keypadPlaceholder = (LinearLayout) findViewById(R.id.level_keypad_keys_placeholder);

            int rows = game.getOptions().getKeypadRows();
            int columns = game.getOptions().getKeypadColumns();

            for (int i = 0; i < rows; i++) {
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

                boolean isFirstRow = i == 0;
                if (isFirstRow) {
                    layoutParams.setMargins(0, 0, 0, 2);
                } else if (i == rows - 1) {
                    layoutParams.setMargins(0, 2, 0, 0);
                }

                keypadPlaceholder.addView(layout, layoutParams);

                for (int j = 0; j < columns; j++) {
                    LetterButton button = new LetterButton(getContext());

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button b = (Button) view;
                            Toast.makeText(b.getContext(), b.getText(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                        0, ViewGroup.LayoutParams.MATCH_PARENT, 1);

                    boolean isFirstColumn = j == 0;
                    boolean isLastColumn = j == columns - 1;

                    if (isFirstColumn) {
                        buttonParams.setMargins(0, 0, 2, 0);
                    } else if (isLastColumn) {
                        buttonParams.setMargins(2, 0, 0, 0);
                    } else {
                        buttonParams.setMargins(2, 0, 2, 0);
                    }

                    layout.addView(button, buttonParams);
                }
            }
        }
    }

    private void initializeActionButton() {
        Button helpButton = (Button) findViewById(R.id.level_help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Help touched", Toast.LENGTH_SHORT).show();
            }
        });

        Game game = Configuration.getInstance().getGame();

        if (game != null) {
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
    }

    private void setupKeypad(Level level) {
        ViewGroup keypad = (ViewGroup) findViewById(R.id.level_keypad);
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
            Button key = (Button) keys.get(i);
            key.setText(letters.get(i));
        }
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
}
