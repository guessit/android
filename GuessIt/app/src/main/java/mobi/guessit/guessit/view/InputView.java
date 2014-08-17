package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.model.Level;

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

        getKeypadView().setLevel(level);
        getAnswerView().setAnswer(level.getAnswer());
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
}
