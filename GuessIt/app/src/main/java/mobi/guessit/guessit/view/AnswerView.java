package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.UserInterface;

public class AnswerView extends LinearLayout {

    public static final int AFTER_SPACE_MARGIN = 20;

    private UserInterface ui;

    private String answer;
    private List<PlaceholderView> placeholderViews;

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

        this.setBackgroundColor(ColorHelper.parseColor(ui.getAnswer().getBackgroundColor()));
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;

        this.adjustChildViews();
    }

    public List<PlaceholderView> getPlaceholderViews() {
        if (placeholderViews == null) {
            placeholderViews = new LinkedList<PlaceholderView>();
        }
        return placeholderViews;
    }

    private PlaceholderView getPlaceholderAtIndex(int index) {
        PlaceholderView placeholder = null;

        if (this.getPlaceholderViews().size() > index) {
            placeholder = this.getPlaceholderViews().get(index);
        } else {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            placeholder = (PlaceholderView) inflater.inflate(
                R.layout.placeholder_view, this, false);
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
        PlaceholderView placeholderView = this.getPlaceholderAtIndex(letterIndex);
        placeholderView.setLetter(letter);

        if (previousLetterWasSpace) {
            LinearLayout.LayoutParams layoutParams = (LayoutParams) placeholderView.getLayoutParams();
            layoutParams.leftMargin += getResources().getDimension(
                R.dimen.level_placeholder_margin_after_space);
        }

        this.addView(placeholderView);
    }
}
