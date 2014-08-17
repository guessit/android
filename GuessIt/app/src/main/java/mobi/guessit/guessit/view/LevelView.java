package mobi.guessit.guessit.view;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BackgroundHelper;
import mobi.guessit.guessit.helper.BumpAnimator;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
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

    public void setLevel(Level level) {
        this.level = level;
        getInputView().setLevel(level);
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

        BumpAnimator.getInstance().animateIn(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BumpAnimator.getInstance().animateIn(view);
                Level level = new Level();

                int index = new Random().nextInt(2);
                String answer = "BRASIL";
                if (index == 0) {
                    answer = "UNITED STATES";
                }

                level.setAnswer(answer);
                LevelView.this.setLevel(level);
            }
        });
    }

    private InputView inputView;
    private InputView getInputView() {
        if (inputView == null) {
            inputView = (InputView) findViewById(R.id.level_input_view);
        }
        return inputView;
    }
}
