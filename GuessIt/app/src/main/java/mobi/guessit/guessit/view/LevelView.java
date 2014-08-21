package mobi.guessit.guessit.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BackgroundHelper;
import mobi.guessit.guessit.helper.BumpAnimator;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.GuessingResult;
import mobi.guessit.guessit.model.Level;
import mobi.guessit.guessit.model.UserInterfaceElement;

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
        setLevel(level, true);
    }

    public void setLevel(Level level, boolean animated) {
        this.level = level;
        getInputView().setLevel(level, animated);

        ImageView imageView = (ImageView) findViewById(R.id.level_image_view);
        imageView.setImageDrawable(level.getImage());

        if (animated) {
            BumpAnimator.getInstance().animateIn(imageView);
        }
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
            inputView.setOnFinishGuessingListener(new InputView.OnFinishGuessingListener() {
                @Override
                public void onFinishGuessing(String answer) {
                    GuessingResult result = getLevel().guessWithAnswer(answer);

                    if (result == GuessingResult.CORRECT) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.congratulations_dialog);
                        dialog.getWindow().setBackgroundDrawable(
                                new ColorDrawable(android.R.color.transparent));

                        setupCongratulationsDialog(dialog, answer);

                        dialog.show();
                    }
                }
            });
        }
        return inputView;
    }

    private void setupCongratulationsDialog(final Dialog dialog, String correctAnswer) {
        View layout = dialog.findViewById(R.id.alert_congratulations_layout);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                Level nextLevel = Configuration.getInstance().getCurrentLevel();
                nextLevel.loadResources(getContext());
                setLevel(nextLevel);
            }
        });

        UserInterfaceElement ui = Configuration.getInstance().getGame().
                getUserInterface().getCongratulations();

        TextView titleTextView = (TextView) dialog.findViewById(R.id.alert_congratulations_text_view);
        titleTextView.setTextColor(ColorHelper.parseColor(ui.getTextColor()));
        titleTextView.setShadowLayer(1, 0, -1, ColorHelper.parseColor(ui.getShadowColor()));
        titleTextView.setRotation(-4);

        TextView subtitleTextView = (TextView) dialog.findViewById(R.id.alert_correct_answer_text_view);
        subtitleTextView.setRotation(-4);

        TextView answerTextView = (TextView) dialog.findViewById(R.id.alert_answer_text_view);
        answerTextView.setText(getLevel().getAnswer());

        PaintDrawable background = new PaintDrawable(ColorHelper.parseColor("#000000"));
        background.setCornerRadius(getResources().getDimension(
                R.dimen.congratulations_answer_corner_radius));
        BackgroundHelper.getInstance().setBackground(answerTextView, background);
    }

    private Bitmap takeScreenShot() {
        setDrawingCacheEnabled(true);
        buildDrawingCache(true);
        return Bitmap.createBitmap(getDrawingCache());
    }
}
