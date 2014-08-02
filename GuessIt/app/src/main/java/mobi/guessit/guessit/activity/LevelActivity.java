package mobi.guessit.guessit.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mobi.guessit.guessit.R;
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
        background.setBackgroundColor(Color.parseColor(main.getBackgroundColor()));

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

        UserInterfaceElement answerUI = game.getUserInterface().getAnswer();
        View answerView = findViewById(R.id.level_answer);
        answerView.setBackgroundColor(Color.parseColor(answerUI.getBackgroundColor()));

        UserInterfaceElement keypadUI = game.getUserInterface().getKeypad();
        View keypadView = findViewById(R.id.level_keypad);
        keypadView.setBackgroundColor(Color.parseColor(keypadUI.getBackgroundColor()));
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
