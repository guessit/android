package mobi.guessit.guessit.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage(R.string.congrats_message)
                               .setNeutralButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {
                                       Level nextLevel = Configuration.getInstance().getCurrentLevel();
                                       nextLevel.loadResources(getContext());
                                       setLevel(nextLevel);
                                   }
                               });
                        builder.create().show();
                    }

                }
            });
        }
        return inputView;
    }
}
