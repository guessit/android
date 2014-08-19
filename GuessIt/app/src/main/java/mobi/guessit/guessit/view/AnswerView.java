package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class AnswerView extends LinearLayout {

    private String correctAnswer;

    private List<PlaceholderView> placeholderViews;
    private OnAnswerListener onAnswerListener;

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
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupUI();
    }

    private void setupUI() {
        Game game = Configuration.getInstance().getGame();

        if (game != null) {
            UserInterfaceElement ui = game.getUserInterface().getAnswer();
            setBackgroundColor(ColorHelper.parseColor(ui.getBackgroundColor()));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (getCorrectAnswer() != null) {
            adjustChildViews();
        }
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;

        for (PlaceholderView placeholder : getPlaceholderViews()) {
            placeholder.reset();
        }

        onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
    }

    public String getCurrentAnswer() {
        String currentAnswer = "";

        for (PlaceholderView placeholder : getActivePlaceholderViews()) {
            String letter = "*";

            String placeholderLetter = placeholder.getLetter();
            if (placeholderLetter != null) {
                letter = placeholderLetter;
            }

            currentAnswer += letter;
        }

        return currentAnswer;
    }

    public List<PlaceholderView> getPlaceholderViews() {
        if (placeholderViews == null) {
            placeholderViews = new LinkedList<PlaceholderView>();
        }
        return placeholderViews;
    }

    public List<PlaceholderView> getActivePlaceholderViews() {
        List<PlaceholderView> placeholders = new LinkedList<PlaceholderView>();

        for (PlaceholderView placeholder : getPlaceholderViews()) {
            if (placeholder.getParent() != null) {
                placeholders.add(placeholder);
            }
        }

        return placeholders;
    }

    public OnAnswerListener getOnAnswerListener() {
        return onAnswerListener;
    }

    public void setOnAnswerListener(OnAnswerListener onAnswerListener) {
        this.onAnswerListener = onAnswerListener;
    }

    private PlaceholderView getPlaceholderAtIndex(int index) {
        PlaceholderView placeholder;

        if (getPlaceholderViews().size() > index) {
            placeholder = getPlaceholderViews().get(index);
        } else {
            LayoutInflater inflater = (LayoutInflater) getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            placeholder = (PlaceholderView) inflater.inflate(
                R.layout.placeholder_view, this, false);
            placeholder.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlaceholderView placeholder = (PlaceholderView) view;
                    if (placeholder.canRemoveLetter()) {
                        getOnAnswerListener().onLetterRemoved(AnswerView.this, placeholder);
                    }
                }
            });
            placeholder.getLetterButton().setClickable(false);
            placeholder.getLetterButton().setFocusable(false);

            getPlaceholderViews().add(placeholder);
        }

        return placeholder;
    }

    private void adjustChildViews() {
        removeAllViews();

        int answerLength = getCorrectAnswer().length();

        int letterIndex = 0;
        boolean previousLetterWasSpace = false;

        float initialWidth = getResources().getDimension(R.dimen.level_placeholder_width);
        float initialHeight = getResources().getDimension(R.dimen.level_placeholder_height);
        float margin = getResources().getDimension(R.dimen.level_placeholder_margin);
        float marginAfterSpace = getResources().getDimension(R.dimen.level_placeholder_margin_after_space);
        float ratio = initialWidth / initialHeight;

        int noSpaces = getCorrectAnswer().split(" ").length - 1;
        float totalMargin = (2 + noSpaces) * marginAfterSpace + answerLength * margin * 2;

        float width = (getWidth() - totalMargin) / answerLength;
        if (width > initialWidth) {
            width = initialWidth;
        }

        float height = width / ratio;

        for (int i = 0; i < answerLength; i++) {
            String letter = String.valueOf(getCorrectAnswer().charAt(i));

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
        PlaceholderView placeholderView = getPlaceholderAtIndex(letterIndex);
        placeholderView.setLetter(letter);

        LinearLayout.LayoutParams layoutParams = (LayoutParams) placeholderView.getLayoutParams();

        layoutParams.width = (int) width;
        layoutParams.height = (int) height;

        float margin = getResources().getDimension(R.dimen.level_placeholder_margin);

        if (previousLetterWasSpace) {
            margin += getResources().getDimension(R.dimen.level_placeholder_margin_after_space);
        }

        layoutParams.leftMargin = (int) margin;

        addView(placeholderView, layoutParams);
    }

    public boolean canAddLetter(LetterButton letter) {
        boolean canAdd = false;

        for (PlaceholderView placeholder : getActivePlaceholderViews()) {
            if (placeholder.canDisplayLetter(letter)) {
                canAdd = true;
                break;
            }
        }

        return canAdd;
    }

    public void addLetter(LetterButton letter) {
        for (PlaceholderView placeholder : getPlaceholderViews()) {
            if (placeholder.canDisplayLetter(letter)) {
                placeholder.displayLetter(letter);
                break;
            }
        }
    }

    public interface OnAnswerListener {
        void onLetterRemoved(AnswerView answerView, PlaceholderView placeholder);
    }
}
