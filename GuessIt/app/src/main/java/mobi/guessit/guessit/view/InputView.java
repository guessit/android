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

    private KeypadView keypadView;
    private KeypadView getKeypadView() {
        if (keypadView == null) {
            keypadView = (KeypadView) findViewById(R.id.level_keypad_view);
        }
        return keypadView;
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
            Button key = (Button) keys.get(i);
            key.setText(letters.get(i));
        }
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
}
