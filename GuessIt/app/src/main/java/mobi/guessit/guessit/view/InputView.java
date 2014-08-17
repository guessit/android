package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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
            keypadView.setKeypadListener(new KeypadView.OnKeypadListener() {
                @Override
                public boolean canAddLetter(KeypadView keypad, LetterButton letter) {
                    return getAnswerView().canAddLetter(letter);
                }

                @Override
                public void onLetterAdded(KeypadView keypad, LetterButton letter) {
                    getAnswerView().addLetter(letter);
                }

                @Override
                public void onHelpRequested(KeypadView keypad, Button helpButton) {
                    Toast.makeText(getContext(), "HELP!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return keypadView;
    }
}
