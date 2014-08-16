package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.model.UserInterface;

public class AnswerView extends LinearLayout {

    public static final int AFTER_SPACE_MARGIN = 20;

    private UserInterface ui;

    private String answer;
    private List<AnswerPlaceholderView> placeholderViews;

    public AnswerView(Context context) {
        super(context);
    }

    public AnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnswerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public UserInterface getUI() {
        return ui;
    }

    public void setUI(UserInterface ui) {
        this.ui = ui;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;

        this.adjustChildViews();
    }

    public List<AnswerPlaceholderView> getPlaceholderViews() {
        if (placeholderViews == null) {
            placeholderViews = new LinkedList<AnswerPlaceholderView>();
        }
        return placeholderViews;
    }

    private AnswerPlaceholderView getPlaceholderAtIndex(int index) {
        AnswerPlaceholderView placeholder = null;

        if (this.getPlaceholderViews().size() > index) {
            placeholder = this.getPlaceholderViews().get(index);
        } else {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            placeholder = (AnswerPlaceholderView) inflater.inflate(
                R.layout.answer_placeholder_view, this, false);
            placeholder.setPlaceholderUI(this.getUI().getPlaceholder());
            placeholder.setLetterUI(this.getUI().getLetter());

            this.getPlaceholderViews().add(placeholder);
        }

        return placeholder;
    }

    private void adjustChildViews() {
        this.removeAllViews();

        int letterIndex = 0;
        boolean previousLetterWasSpace = false;

        for (int i = 0; i < this.answer.length(); i++) {
            String letter = String.valueOf(this.answer.charAt(i));

            boolean isSpace = letter.equals(" ");
            if (isSpace) {
                previousLetterWasSpace = true;
            } else {
                setupPlaceholder(letter, letterIndex, previousLetterWasSpace);
                letterIndex++;
                previousLetterWasSpace = false;
            }
        }
    }

    private void setupPlaceholder(String letter, int letterIndex, boolean previousLetterWasSpace) {
        AnswerPlaceholderView placeholderView = this.getPlaceholderAtIndex(letterIndex);
        placeholderView.setLetter(letter);

        if (previousLetterWasSpace) {
            MarginLayoutParams marginLayoutParams = new MarginLayoutParams(
                placeholderView.getLayoutParams());
            marginLayoutParams.leftMargin = AFTER_SPACE_MARGIN;

            placeholderView.setLayoutParams(marginLayoutParams);
        }

        this.addView(placeholderView);
    }
}
