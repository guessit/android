package mobi.guessit.framework.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import mobi.guessit.framework.R;
import mobi.guessit.framework.helper.BackgroundHelper;
import mobi.guessit.framework.helper.BumpAnimator;
import mobi.guessit.framework.helper.ColorHelper;
import mobi.guessit.framework.helper.ResourceHelper;
import mobi.guessit.framework.model.Configuration;
import mobi.guessit.framework.model.Game;
import mobi.guessit.framework.model.GuessingResult;
import mobi.guessit.framework.model.Level;
import mobi.guessit.framework.model.UserInterfaceElement;

public class LevelView extends RelativeLayout {

    private OnLevelGuessedCorrect onLevelGuessedCorrect;

    private Level level;
    private CongratulationsDialog congratulationsDialog;

    public LevelView(Context context) {
        super(context);
    }

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LevelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public OnLevelGuessedCorrect getOnLevelGuessedCorrect() {
        return onLevelGuessedCorrect;
    }

    public void setOnLevelGuessedCorrect(OnLevelGuessedCorrect onLevelGuessedCorrect) {
        this.onLevelGuessedCorrect = onLevelGuessedCorrect;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        setLevel(level, true);
    }

    public void setLevel(Level level, boolean animated) {
        this.level = level;
        this.level.loadAnswerI18n(getContext());

        Configuration.getInstance().trackEvent(
            Configuration.Events.GAME_CATEGORY,
            Configuration.Events.LEVEL_LOADED,
            this.level.getImageName()
        );

        getInputView().setLevel(level, animated);

        ImageView imageView = (ImageView) findViewById(R.id.level_image_view);

        ResourceHelper helper = ResourceHelper.getInstance();
        Drawable image = helper.getImage(getContext(), level.getKeyName());
        imageView.setImageDrawable(image);

        if (animated) {
            BumpAnimator.getInstance().animateIn(imageView);
        }
    }

    public CongratulationsDialog getCongratulationsDialog() {
        if (congratulationsDialog == null) {
            congratulationsDialog = new CongratulationsDialog(getContext());
            congratulationsDialog.setOnDialogDismissed(new CongratulationsDialog.OnDialogDismissed() {
                @Override
                public void onDialogDismissed(CongratulationsDialog dialog) {
                    dismissCongratulations();
                }
            });
        }
        return congratulationsDialog;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setupUI();
    }

    private void setupUI() {
        Game game = Configuration.getInstance().getGame();

        if (game != null) {
            UserInterfaceElement level =  game.getUserInterface().getLevel();

            setBackgroundColor(ColorHelper.parseColor(level.getBackgroundColor()));

            initializeImageView();
        }
    }

    private void initializeImageView() {
        UserInterfaceElement frame = Configuration.getInstance().getGame().
            getUserInterface().getFrame();

        PaintDrawable background = new PaintDrawable(ColorHelper.parseColor(
            frame.getBackgroundColor()));
        background.setCornerRadius(getResources().getDimension(
            R.dimen.level_image_view_corner_radius));

        ImageView imageView = (ImageView) findViewById(R.id.level_image_view);
        BackgroundHelper.getInstance().setBackground(imageView, background);
    }

    private InputView inputView;
    private InputView getInputView() {
        if (inputView == null) {
            inputView = (InputView) findViewById(R.id.level_input_view);
            inputView.setOnLetterAddedToAnswerListener(new InputView.OnLetterAddedToAnswerListener() {
                @Override
                public void onLetterAddedToAnswer(String letter) {
                }
            });

            inputView.setOnLetterRemovedFromAnswerListener(new InputView.OnLetterRemovedFromAnswerListener() {
                @Override
                public void onLetterRemovedFromAnswer(String letter) {
                }
            });

            inputView.setOnLevelSkippedListener(new InputView.OnLevelSkippedListener() {
                @Override
                public void onLevelSkipped(Level oldLevel, Level nextLevel) {
                    setLevel(nextLevel);
                }
            });

            inputView.setOnFinishGuessingListener(new InputView.OnFinishGuessingListener() {
                @Override
                public void onFinishGuessing(String answer) {
                    GuessingResult result = getLevel().guessWithAnswer(answer);

                    if (result == GuessingResult.CORRECT) {
                        showCongratulationsDialog();
                    }
                }
            });
        }
        return inputView;
    }

    public boolean isShowingCongratulations() {
        return getCongratulationsDialog().isShowing();
    }

    public void dismissCongratulations() {
        getCongratulationsDialog().dismiss();

        Level previousLevel = getLevel();

        Level nextLevel = Configuration.getInstance().getCurrentLevel();
        nextLevel.loadAnswerI18n(getContext());
        setLevel(nextLevel);

        if (getOnLevelGuessedCorrect() != null) {
            getOnLevelGuessedCorrect().onLevelGuessedCorrect(this, previousLevel);
        }
    }

    private void showCongratulationsDialog() {
        getCongratulationsDialog().setCorrectAnswer(getLevel().getAnswer()).show();
    }

    public interface OnLevelGuessedCorrect {
        public void onLevelGuessedCorrect(LevelView levelView, Level level);
    }
}
