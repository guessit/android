package mobi.guessit.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

import mobi.guessit.framework.R;
import mobi.guessit.framework.helper.ColorHelper;
import mobi.guessit.framework.model.Configuration;
import mobi.guessit.framework.model.Game;
import mobi.guessit.framework.model.Level;
import mobi.guessit.framework.model.UserInterfaceElement;

public class AnswerView extends LinearLayout {

    private Level level;

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

        if (getLevel() != null) {
            adjustChildViews();
        }
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;

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

    public String getMissingLetters() {
        String missingLetters = "";

        String currentAnswer = getCurrentAnswer();
        String noSpacesAnswer = getLevel().getNoSpacesAnswer();

        for (int i = 0; i < noSpacesAnswer.length(); i++) {
            String letter = "*";

            if (currentAnswer.charAt(i) == '*') {
                letter = String.valueOf(noSpacesAnswer.charAt(i));
            }

            missingLetters += letter;
        }

        return missingLetters;
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

        String correctAnswer = getLevel().getAnswer();
        int answerLength = correctAnswer.length();

        int letterIndex = 0;
        boolean previousLetterWasSpace = false;

        float initialWidth = getResources().getDimension(R.dimen.level_placeholder_width);
        float initialHeight = getResources().getDimension(R.dimen.level_placeholder_height);
        float margin = getResources().getDimension(R.dimen.level_placeholder_margin);
        float marginAfterSpace = getResources().getDimension(R.dimen.level_placeholder_margin_after_space);
        float ratio = initialWidth / initialHeight;

        boolean isLongWord = answerLength >= 10;

        int noSpaces = correctAnswer.split(" ").length - 1;

        float totalLeftMargin = (answerLength - 1) * margin;
        float totalRightMargin = (answerLength - 1) * margin;
        float totalSpacesMargin = noSpaces * marginAfterSpace;
        float totalPadding = 2 * marginAfterSpace;

        if (isLongWord) {
            totalLeftMargin /= 2;
        }

        float totalMargin = totalLeftMargin + totalRightMargin + totalSpacesMargin + totalPadding;

        float width = (getWidth() - totalMargin) / answerLength;
        if (width > initialWidth) {
            width = initialWidth;
        }

        float height = width / ratio;

        for (int i = 0; i < answerLength; i++) {
            String letter = String.valueOf(correctAnswer.charAt(i));

            boolean isSpace = letter.equals(" ");
            if (isSpace) {
                previousLetterWasSpace = true;
            } else {
                boolean isFirstLetter = i == 0;
                boolean isLastLetter = i == answerLength - 1;

                setupPlaceholder(letter, letterIndex, width, height, previousLetterWasSpace,
                        isFirstLetter, isLastLetter, isLongWord);
                letterIndex++;
                previousLetterWasSpace = false;
            }
        }
    }

    private void setupPlaceholder(String letter, int letterIndex,
                                  float width, float height,
                                  boolean previousLetterWasSpace,
                                  boolean isFirstLetter,
                                  boolean isLastLetter,
                                  boolean isLongWord) {
        PlaceholderView placeholderView = getPlaceholderAtIndex(letterIndex);
        placeholderView.setLetter(letter);

        LayoutParams layoutParams = (LayoutParams) placeholderView.getLayoutParams();

        layoutParams.width = (int) width;
        layoutParams.height = (int) height;

        float leftMargin = getResources().getDimension(R.dimen.level_placeholder_margin);
        float rightMargin = getResources().getDimension(R.dimen.level_placeholder_margin);

        if (isLongWord) {
            leftMargin /= 2;
        }

        if (previousLetterWasSpace) {
            leftMargin += getResources().getDimension(R.dimen.level_placeholder_margin_after_space);
        }

        if (isFirstLetter) {
            leftMargin = 0;
        }

        if (isLastLetter) {
            rightMargin = 0;
        }

        layoutParams.leftMargin = (int) leftMargin;
        layoutParams.rightMargin = (int) rightMargin;

        addView(placeholderView, layoutParams);
    }

    public boolean canAddLetter() {
        boolean canAdd = false;

        for (PlaceholderView placeholder : getActivePlaceholderViews()) {
            if (placeholder.canDisplayLetter()) {
                canAdd = true;
                break;
            }
        }

        return canAdd;
    }

    public void addLetter(LetterButton letter) {
        for (PlaceholderView placeholder : getPlaceholderViews()) {
            if (placeholder.canDisplayLetter()) {
                placeholder.displayLetter(letter);
                break;
            }
        }
    }

    public void addLetterToCorrectPlace(LetterButton letterButton) {
        String missingLetters = getMissingLetters();
        for (int i = 0; i < missingLetters.length(); i++) {
            char letter = missingLetters.charAt(i);
            if (letter == letterButton.getLetter().charAt(0)) {
                PlaceholderView placeholder = getActivePlaceholderViews().get(i);
                placeholder.displayLetter(letterButton);
                break;
            }
        }
    }

    public interface OnAnswerListener {
        void onLetterRemoved(AnswerView answerView, PlaceholderView placeholder);
    }
}
