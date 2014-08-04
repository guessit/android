package mobi.guessit.guessit.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.StateSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.UserInterfaceElement;

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

            if (i == 0) {
                layoutParams.setMargins(0, 0, 0, 2);
            } else if (i == rows - 1) {
                layoutParams.setMargins(0, 2, 0, 0);
            }

            keypadPlaceholder.addView(layout, layoutParams);

            for (int j = 0; j < columns; j++) {
                Button button = new Button(this);
                button.setText("A" + i + j);
                button.setTextSize(11);
                button.setBackgroundColor(ColorHelper.parseColor(letterUI.getBackgroundColor()));
                button.setTextColor(ColorHelper.parseColor(letterUI.getTextColor()));
                button.setShadowLayer(1, 0, -1, ColorHelper.parseColor(letterUI.getShadowColor()));

                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 1);

                if (j == 0) {
                    buttonParams.setMargins(0, 0, 2, 0);
                } else if (j == columns - 1) {
                    buttonParams.setMargins(2, 0, 0, 0);
                } else {
                    buttonParams.setMargins(2, 0, 2, 0);
                }

                layout.addView(button, buttonParams);
            }
        }
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
