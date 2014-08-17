package mobi.guessit.guessit.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BackgroundHelper;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.Level;
import mobi.guessit.guessit.model.UserInterfaceElement;
import mobi.guessit.guessit.view.AnswerView;
import mobi.guessit.guessit.view.LetterButton;
import mobi.guessit.guessit.view.LevelView;

public class LevelActivity extends Activity {

    private LevelView levelView;

    public LevelView getLevelView() {
        if (levelView == null) {
            levelView = (LevelView) findViewById(R.id.level_view);
        }
        return levelView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        initializeView();

        Level currentDummyLevel = new Level();
        currentDummyLevel.setAnswer("BRAZIL");

        this.getLevelView().setLevel(currentDummyLevel);
    }

    private void setupActionBar(Game game) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setIcon(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setBackgroundDrawable(getActionBarBackground(game));

        View layout = LayoutInflater.from(this).inflate(R.layout.action_bar, null);

        final int navColor = getNavColor(game);

        Button backButton = (Button) layout.findViewById(R.id.action_bar_back_button);
        backButton.setTextColor(navColor);
        backButton.setText("<");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LevelActivity.this.onBackPressed();
            }
        });

        UserInterfaceElement titleElement = game.getUserInterface().getTitle();

        TextView guessItTextView = (TextView) layout.findViewById(R.id.action_bar_guessit_text_view);
        guessItTextView.setTextColor(ColorHelper.parseColor(
            titleElement.getTextColor()));
        guessItTextView.setShadowLayer(1, 0, -1, ColorHelper.parseColor(
            titleElement.getShadowColor()));

        Button settingsButton = (Button) layout.findViewById(R.id.action_bar_settings_button);
        settingsButton.setTextColor(navColor);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LevelActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            }
        });

        actionBar.setCustomView(layout);
    }

    private ColorDrawable getActionBarBackground(Game game) {
        return new ColorDrawable(ColorHelper.parseColor(
            game.getUserInterface().getNavigation().getBackgroundColor()));
    }

    private int getNavColor(Game game) {
        return ColorHelper.parseColor(game.getUserInterface().getNavigation().getColor());
    }

    private void initializeView() {
        Game game = Configuration.getInstance().getGame();

        setupActionBar(game);

        UserInterfaceElement main = game.getUserInterface().getLevel();

        this.getLevelView().setBackgroundColor(ColorHelper.parseColor(main.getBackgroundColor()));

        // secondary background image view
        //   used when there's a background image instead of just a background color
        //   for now its used only on Vida de Programador game

        // category label
        //   label to show category when level has one

        // image view frame
        //   just a view that contains the image to be guessed
        //   used to show a small border around the image to be guessed

        // input view
        //   the view that contains the keyboard and anwser placeholders
        //   fragment?
        View inputView = findViewById(R.id.level_input_view);

        initializeAnswerView(game);
        initializeKeypad(game);
        initializeActionButton(game);
        initializeImageView(game);
    }

    private void initializeAnswerView(Game game) {
        AnswerView answerView = (AnswerView) findViewById(R.id.answer_view);
        answerView.setUI(game.getUserInterface());
    }

    private void initializeKeypad(Game game) {
        UserInterfaceElement keypadUI = game.getUserInterface().getKeypad();
        UserInterfaceElement letterUI = game.getUserInterface().getLetter();

        View keypadView = findViewById(R.id.level_keypad);
        keypadView.setBackgroundColor(ColorHelper.parseColor(keypadUI.getBackgroundColor()));

        LinearLayout keypadPlaceholder = (LinearLayout) findViewById(R.id.level_keypad_keys_placeholder);

        int rows = game.getOptions().getKeypadRows();
        int columns = game.getOptions().getKeypadColumns();

        for (int i = 0; i < rows; i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

            boolean isFirstRow = i == 0;
            if (isFirstRow) {
                layoutParams.setMargins(0, 0, 0, 2);
            } else if (i == rows - 1) {
                layoutParams.setMargins(0, 2, 0, 0);
            }

            keypadPlaceholder.addView(layout, layoutParams);

            for (int j = 0; j < columns; j++) {
                LetterButton button = new LetterButton(this);
                button.setLetterUI(letterUI);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button b = (Button) view;
                        Toast.makeText(LevelActivity.this, b.getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 1);

                boolean isFirstColumn = j == 0;
                boolean isLastColumn = j == columns - 1;

                if (isFirstColumn) {
                    buttonParams.setMargins(0, 0, 2, 0);
                } else if (isLastColumn) {
                    buttonParams.setMargins(2, 0, 0, 0);
                } else {
                    buttonParams.setMargins(2, 0, 2, 0);
                }

                layout.addView(button, buttonParams);
            }
        }
    }

    private void initializeActionButton(Game game) {
        Button helpButton = (Button) findViewById(R.id.level_help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Help touched", Toast.LENGTH_SHORT).show();
            }
        });

        UserInterfaceElement actionUI = game.getUserInterface().getAction();

        helpButton.setBackgroundColor(ColorHelper.parseColor(actionUI.getBackgroundColor()));
        helpButton.setShadowLayer(1, 0, -1,
            ColorHelper.parseColor(actionUI.getShadowColor()));

        ColorStateList colors = new ColorStateList(new int[][]{
            new int[]{android.R.attr.state_pressed},
            StateSet.WILD_CARD
        }, new int[]{
            ColorHelper.parseColor(actionUI.getSecondaryTextColor()),
            ColorHelper.parseColor(actionUI.getTextColor()),
        });

        helpButton.setTextColor(colors);
    }

    private void initializeImageView(Game game) {
        PaintDrawable background = new PaintDrawable(ColorHelper.parseColor(
            game.getUserInterface().getFrame().getBackgroundColor()));
        background.setCornerRadius(2.f);

        ImageView imageView = (ImageView) findViewById(R.id.level_image_view);
        BackgroundHelper.getInstance().setBackground(imageView, background);

        animateImageView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LevelActivity.this.animateImageView((ImageView) view);
                Level level = new Level();

                int index = new Random().nextInt(2);
                String answer = "BRASIL";
                if (index == 0) {
                    answer = "UNITED STATES";
                }

                level.setAnswer(answer);
                LevelActivity.this.getLevelView().setLevel(level);
            }
        });
    }

    private void animateImageView(ImageView imageView) {
        imageView.setScaleX(0.01f);
        imageView.setScaleY(0.01f);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.f);

        AnimatorSet bumpScale = new AnimatorSet();
        bumpScale.setInterpolator(new OvershootInterpolator());
        bumpScale.play(scaleX).with(scaleY);
        bumpScale.start();
    }
}
