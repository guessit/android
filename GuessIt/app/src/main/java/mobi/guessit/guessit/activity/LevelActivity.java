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

        UserInterfaceElement main = game.getUserInterface().getMain();

        View background = findViewById(R.id.level_background);
        background.setBackgroundColor(Color.parseColor(main.getBackgroundColor()));

        // adjust keyboard color
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
