package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.RandomLetterHelper;
import mobi.guessit.guessit.helper.ViewHelper;
import mobi.guessit.guessit.model.Level;

public class LevelView extends RelativeLayout {

    private Level level;

    public LevelView(Context context) {
        super(context);
    }

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LevelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;

        this.setupKeypad();
        this.setupPlaceholder();
    }

    private void setupKeypad() {
        ViewGroup keypad = (ViewGroup) findViewById(R.id.level_keypad);
        List<View> keys = ViewHelper.getViewsWithTag(keypad, "key_button");

        final int answerLength = this.getLevel().getNoSpacesAnswer().length();

        List<String> letters = new ArrayList<String>();
        for (int i = 0; i < answerLength; i++) {
            letters.add(this.getLevel().getLetterAt(i));
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

    private void setupPlaceholder() {
        AnswerView answerView = (AnswerView) findViewById(R.id.answer_view);
        answerView.setAnswer(this.getLevel().getAnswer());
    }
}
