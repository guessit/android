package mobi.guessit.guessit.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.StateSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.helper.ViewHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.UserInterfaceElement;
import mobi.guessit.guessit.view.components.FontFaceButton;

public class LevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        initializeView();
    }

    private void initializeView() {
        Game game = Configuration.getInstance().getGame();

        UserInterfaceElement main = game.getUserInterface().getLevel();

        View background = findViewById(R.id.level_background);
        background.setBackgroundColor(ColorHelper.parseColor(main.getBackgroundColor()));

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

        setupAnswerView(game);
        setupKeypad(game);
        setupActionButton(game);

        dummyKeypad();
    }

    private void setupAnswerView(Game game) {
        UserInterfaceElement answerUI = game.getUserInterface().getAnswer();

        View answerView = findViewById(R.id.level_answer);
        answerView.setBackgroundColor(ColorHelper.parseColor(answerUI.getBackgroundColor()));
    }

    private void setupKeypad(Game game) {
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

                Button button = new Button(this);
                button.setTag("key_button");
                button.setTextSize(18.f);
                button.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Avenir Next Condensed-Medium.ttf"), Typeface.NORMAL);
                button.setBackground(backgroundColor(letterUI));
                button.setTextColor(buttonTextColor(letterUI));
                button.setShadowLayer(1, 0, -1, ColorHelper.parseColor(letterUI.getShadowColor()));

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

    private Drawable backgroundColor(UserInterfaceElement ui) {
        int[] pressedState = new int[] { android.R.attr.state_pressed };

        StateListDrawable backgroundColor = new StateListDrawable();
        backgroundColor.addState(pressedState, new ColorDrawable(
            ColorHelper.parseColor(ui.getSecondaryBackgroundColor())
        ));
        backgroundColor.addState(StateSet.WILD_CARD, new ColorDrawable(
            ColorHelper.parseColor(ui.getBackgroundColor())
        ));

        return backgroundColor;
    }

    private ColorStateList buttonTextColor(UserInterfaceElement ui) {
        int[] pressedState = new int[] { android.R.attr.state_pressed };

        ColorStateList buttonTextColor = new ColorStateList(new int[][]{
            pressedState,
            StateSet.WILD_CARD
        }, new int[] {
            ColorHelper.parseColor(ui.getSecondaryTextColor()),
            ColorHelper.parseColor(ui.getTextColor())
        });

        return buttonTextColor;
    }


    private void setupActionButton(Game game) {
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

    private void dummyKeypad() {
        ViewGroup keypad = (ViewGroup) findViewById(R.id.level_keypad);
        List<View> keys = ViewHelper.getViewsWithTag(keypad, "key_button");

        String answer = "BRAZIL";
        final int answerLength = answer.length();

        List<String> letters = new ArrayList<String>();
        for (int i = 0; i < answerLength; i++) {
            letters.add(String.valueOf(answer.charAt(i)));
        }

        int missingLetterCount = keys.size() - answerLength;
        for (int i = 0; i < missingLetterCount; i++) {
            boolean isEven = i % 2 == 0;
            if (isEven) {
                letters.add(randomVowel());
            } else {
                letters.add(randomConsonant());
            }
        }

        Collections.shuffle(letters);

        for (int i = 0; i < keys.size(); i++) {
            Button key = (Button) keys.get(i);
            key.setText(letters.get(i));
        }
    }

    private String randomVowel() {
        final String vowels = "AEIOU";
        int index = new Random().nextInt(vowels.length());
        return String.valueOf(vowels.charAt(index));
    }

    private String randomConsonant() {
        final String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        int index = new Random().nextInt(consonants.length());
        return String.valueOf(consonants.charAt(index));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.level, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // show settings activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
