package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Level;

public class InputView extends LinearLayout {

    private Level level;

    private OnLetterAddedToAnswerListener onLetterAddedToAnswerListener;
    private OnLetterRemovedFromAnswerListener onLetterRemovedFromAnswerListener;
    private OnLevelSkippedListener onLevelSkippedListener;
    private OnFinishGuessingListener onFinishGuessingListener;

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
        setLevel(level, true);
    }

    public void setLevel(Level level, boolean animated) {
        this.level = level;

        getKeypadView().setLevel(level, animated);
        getAnswerView().setLevel(level);
    }

    public OnLetterAddedToAnswerListener getOnLetterAddedToAnswerListener() {
        return onLetterAddedToAnswerListener;
    }

    public void setOnLetterAddedToAnswerListener(OnLetterAddedToAnswerListener onLetterAddedToAnswerListener) {
        this.onLetterAddedToAnswerListener = onLetterAddedToAnswerListener;
    }

    public OnLetterRemovedFromAnswerListener getOnLetterRemovedFromAnswerListener() {
        return onLetterRemovedFromAnswerListener;
    }

    public void setOnLetterRemovedFromAnswerListener(OnLetterRemovedFromAnswerListener onLetterRemovedFromAnswerListener) {
        this.onLetterRemovedFromAnswerListener = onLetterRemovedFromAnswerListener;
    }

    public OnLevelSkippedListener getOnLevelSkippedListener() {
        return onLevelSkippedListener;
    }

    public void setOnLevelSkippedListener(OnLevelSkippedListener onLevelSkippedListener) {
        this.onLevelSkippedListener = onLevelSkippedListener;
    }

    public OnFinishGuessingListener getOnFinishGuessingListener() {
        return onFinishGuessingListener;
    }

    public void setOnFinishGuessingListener(OnFinishGuessingListener onFinishGuessingListener) {
        this.onFinishGuessingListener = onFinishGuessingListener;
    }

    private AnswerView answerView;
    private AnswerView getAnswerView() {
        if (answerView == null) {
            answerView = (AnswerView) findViewById(R.id.level_answer_view);
            answerView.setOnAnswerListener(new AnswerView.OnAnswerListener() {
                @Override
                public void onLetterRemoved(AnswerView answerView, PlaceholderView placeholder) {
                    getKeypadView().recoverLetter(placeholder.getLetterButton());

                    OnLetterRemovedFromAnswerListener letterRemovedListener =
                        getOnLetterRemovedFromAnswerListener();
                    if (letterRemovedListener != null) {
                        letterRemovedListener.onLetterRemovedFromAnswer(
                            placeholder.getLetterButton().getLetter());
                    }
                }
            });
        }
        return answerView;
    }

    private KeypadView keypadView;
    private KeypadView getKeypadView() {
        if (keypadView == null) {
            keypadView = (KeypadView) findViewById(R.id.level_keypad_view);
            keypadView.setOnKeypadListener(new KeypadView.OnKeypadListener() {
                @Override
                public boolean canAddLetter(KeypadView keypad, LetterButton letter) {
                    return getAnswerView().canAddLetter();
                }

                @Override
                public void onLetterAdded(KeypadView keypad, LetterButton letter) {
                    getAnswerView().addLetter(letter);
                    notifyLetterAddedToAnswer(letter);
                }

                @Override
                public void onHelpRequested(KeypadView keypad, Button helpButton) {
                    getHelpView().show();
                }
            });
        }
        return keypadView;
    }

    private void notifyFinishGuessing() {
        OnFinishGuessingListener listener = getOnFinishGuessingListener();
        if (listener != null) {
            listener.onFinishGuessing(getAnswerView().getCurrentAnswer());
        }
    }

    private void notifyLetterAddedToAnswer(LetterButton letter) {
        OnLetterAddedToAnswerListener listener = getOnLetterAddedToAnswerListener();
        if (listener != null) {
            listener.onLetterAddedToAnswer(letter.getLetter());
        }

        if (!getAnswerView().canAddLetter()) {
            notifyFinishGuessing();
        }
    }

    private HelpView helpView;
    private HelpView getHelpView() {
        if (helpView == null) {
            helpView = new HelpView(getContext());
            helpView.setOnSkipLevelListener(new HelpView.OnSkipLevelListener() {
                @Override
                public void onSkipLevelRequested(HelpView view) {
                    notifyLevelSkipped();

                    Configuration.getInstance().incrementNumberOfHelpRequested();
                    helpView.dismiss();
                }

                @Override
                public boolean canSkipLevel(HelpView view) {
                    return Configuration.getInstance().hasMoreLevels();
                }
            });
            
            helpView.setOnEliminateWrongLetterListener(new HelpView.OnEliminateWrongLetterListener() {
                @Override
                public void onEliminateWrongLetterRequested(HelpView view) {
                    getKeypadView().removeWrongLetter();
                    Configuration.getInstance().incrementNumberOfHelpRequested();
                    helpView.dismiss();
                }

                @Override
                public boolean canEliminateWrongLetter(HelpView view) {
                    return getKeypadView().hasWrongLetterToBeRemoved();
                }
            });
            
            helpView.setOnPlaceCorrectLetterListener(new HelpView.OnPlaceCorrectLetterListener() {
                @Override
                public void onPlaceCorrectLetterRequested(HelpView view) {
                    LetterButton correctLetter = firstCorrectLetterAvailable();
                    getKeypadView().removeLetter(correctLetter);
                    getAnswerView().addLetterToCorrectPlace(correctLetter);
                    notifyLetterAddedToAnswer(correctLetter);

                    Configuration.getInstance().incrementNumberOfHelpRequested();
                    helpView.dismiss();
                }

                @Override
                public boolean canPlaceCorrectLetter(HelpView view) {
                    return getAnswerView().canAddLetter() && hasCorrectLetterAvailable();
                }
            });
        }
        return helpView;
    }

    private void notifyLevelSkipped() {
        Level oldLevel = Configuration.getInstance().getCurrentLevel();
        Level nextLevel = Configuration.getInstance().getNextLevel();

        OnLevelSkippedListener listener = getOnLevelSkippedListener();
        if (listener != null) {
            listener.onLevelSkipped(oldLevel, nextLevel);
        }
    }

    private LetterButton firstCorrectLetterAvailable() {
        String missingLetters = getAnswerView().getMissingLetters();
        return (LetterButton) getKeypadView().getFirstCorrectKey(missingLetters);
    }

    private boolean hasCorrectLetterAvailable() {
        String missingLetters = getAnswerView().getMissingLetters();
        return getKeypadView().hasLetterForAnswer(missingLetters);
    }

    public interface OnLetterAddedToAnswerListener {
        void onLetterAddedToAnswer(String letter);
    }

    public interface OnLetterRemovedFromAnswerListener {
        void onLetterRemovedFromAnswer(String letter);
    }

    public interface OnLevelSkippedListener {
        void onLevelSkipped(Level oldLevel, Level nextLevel);
    }

    public interface OnFinishGuessingListener {
        void onFinishGuessing(String answer);
    }
}
