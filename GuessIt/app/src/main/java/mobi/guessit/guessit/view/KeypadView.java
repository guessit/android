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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BumpAnimator;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.helper.RandomLetterHelper;
import mobi.guessit.guessit.helper.StringHelper;
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

    private Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        setLevel(level, true);
    }

    public void setLevel(Level level, boolean animated) {
        this.level = level;

        this.setupKeypad(level, animated);
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
                        BumpAnimator.getInstance().animateOut(letter);
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

    public void removeLetter(LetterButton letter) {
        BumpAnimator.getInstance().animateOut(letter);
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

    private List<View> getKeys() {
        ViewGroup keypad = (ViewGroup) findViewById(R.id.level_keypad_view);
        return ViewHelper.getViewsWithTag(keypad, "key_button");
    }

    private void setupKeypad(Level level) {
        setupKeypad(level, true);
    }

    private void setupKeypad(Level level, boolean animated) {
        final int answerLength = level.getNoSpacesAnswer().length();
        List<View> keys = getKeys();

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

            if (animated) {
                long delay = new Random().nextInt(250);
                BumpAnimator.getInstance().animateIn(key, delay);
            }
        }
    }

    public void recoverLetter(LetterButton letter) {
        BumpAnimator.getInstance().animateIn(letter.getOriginLetter());
        BumpAnimator.getInstance().animateOut(letter);

        letter.setOriginLetter(null);
    }

    public boolean hasLetterForAnswer(String answer) {
        return getFirstCorrectKey(answer) != null;
    }

    public View getFirstCorrectKey(String answer) {
        View correctLetter = null;

        for (int i = 0; i < answer.length(); i++) {
            char letter = answer.charAt(i);
            if (letter != '*') {
                correctLetter = getLetterButtonForLetter(String.valueOf(letter));
                if (correctLetter != null) break;
            }
        }

        return correctLetter;
    }

    private View getLetterButtonForLetter(String letter) {
        View correctLetter = null;

        for (View view : getKeys()) {
            LetterButton letterButton = (LetterButton) view;
            if (letterButton.isActive() && letterButton.getLetter().equals(letter)) {
                correctLetter = letterButton;
                break;
            }
        }

        return correctLetter;
    }

    public boolean hasWrongLetterToBeRemoved() {
        return getFirstWrongKey() != null;
    }

    public void removeWrongLetter() {
        BumpAnimator.getInstance().animateOut(getFirstWrongKey());
    }

    private View getFirstWrongKey() {
        List<View> keys = getKeys();

        String correctAnswer = getLevel().getAnswer();
        HashMap<String, Integer> letters = new HashMap<String, Integer>(correctAnswer.length());

        List<LetterButton> activeLetters = new LinkedList<LetterButton>();
        List<LetterButton> placedLetters = new LinkedList<LetterButton>();

        for (View view : keys) {
            LetterButton letterButton = (LetterButton) view;

            if (letterButton.isActive()) {
                activeLetters.add(letterButton);
            } else if (letterButton.isPlacedOnAnswer()) {
                placedLetters.add(letterButton);
            }
        }

        for (LetterButton letterButton : placedLetters) {
            increaseLetterCountOnMapWithLetter(letters, letterButton.getLetter());
        }

        View wrongLetter = null;
        for (LetterButton letterButton : activeLetters) {
            String letter = letterButton.getLetter();

            if (!correctAnswer.contains(letter)) {
                wrongLetter = letterButton;
                break;
            } else {
                increaseLetterCountOnMapWithLetter(letters, letter);

                Integer letterCount = letters.get(letterButton.getLetter());
                Integer occurences = StringHelper.countOcurrencesOf(correctAnswer, letter);

                if (letterCount > occurences) {
                    wrongLetter = letterButton;
                    break;
                }
            }
        }

        return wrongLetter;
    }

    private void increaseLetterCountOnMapWithLetter(HashMap<String, Integer> letters, String letter) {
        Integer count = 0;

        if (letters.containsKey(letter)) {
            count = letters.get(letter);
        }

        letters.put(letter, count + 1);
    }

    public interface OnKeypadListener {
        boolean canAddLetter(KeypadView keypad, LetterButton letter);
        void onLetterAdded(KeypadView keypad, LetterButton letter);
        void onHelpRequested(KeypadView keypad, Button helpButton);
    }
}
