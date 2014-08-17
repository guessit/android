package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.UserInterface;

public class AnswerView extends LinearLayout {

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (this.answer != null) {
            this.adjustChildViews();
        }
    }

    public UserInterface getUI() {
        return ui;
    }

    public void setUI(UserInterface ui) {
        this.ui = ui;

        this.setBackgroundColor(ColorHelper.parseColor(ui.getAnswer().getBackgroundColor()));
    }

    public void setAnswer(String answer) {
        this.answer = answer;

        this.onSizeChanged(this.getWidth(), this.getHeight(),
            this.getWidth(), this.getHeight());
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

        int answerLength = this.answer.length();

        int letterIndex = 0;
        boolean previousLetterWasSpace = false;

        float initialWidth = getResources().getDimension(R.dimen.level_placeholder_width);
        float initialHeight = getResources().getDimension(R.dimen.level_placeholder_height);
        float margin = getResources().getDimension(R.dimen.level_placeholder_margin);
        float marginAfterSpace = getResources().getDimension(R.dimen.level_placeholder_margin_after_space);
        float ratio = initialWidth / initialHeight;

        int noSpaces = this.answer.split(" ").length - 1;
        float totalMargin = (2 + noSpaces) * marginAfterSpace + (answerLength - noSpaces - 1) * margin;

        float width = (this.getWidth() - totalMargin) / answerLength;
        if (width > initialWidth) {
            width = initialWidth;
        }

        float height = width / ratio;

        for (int i = 0; i < answerLength; i++) {
            String letter = String.valueOf(this.answer.charAt(i));

            boolean isSpace = letter.equals(" ");
            if (isSpace) {
                previousLetterWasSpace = true;
            } else {
                setupPlaceholder(letter, letterIndex, width, height, previousLetterWasSpace);
                letterIndex++;
                previousLetterWasSpace = false;
            }
        }
    }

    private void setupPlaceholder(String letter, int letterIndex,
                                  float width, float height,
                                  boolean previousLetterWasSpace) {
        PlaceholderView placeholderView = this.getPlaceholderAtIndex(letterIndex);
        placeholderView.setLetter(letter);

        LinearLayout.LayoutParams layoutParams = (LayoutParams) placeholderView.getLayoutParams();

        layoutParams.width = (int) width;
        layoutParams.height = (int) height;

        float margin = getResources().getDimension(R.dimen.level_placeholder_margin);

        if (previousLetterWasSpace) {
            margin += getResources().getDimension(R.dimen.level_placeholder_margin_after_space);
        }

        layoutParams.leftMargin = (int) margin;

        this.addView(placeholderView, layoutParams);
    }
}
