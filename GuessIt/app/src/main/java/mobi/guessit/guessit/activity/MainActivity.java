package mobi.guessit.guessit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.FileHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration config = Configuration.getInstance();
        config.setGame(Game.fromJson(loadJSONFile()));

        initializeView();
    }

    private String loadJSONFile() {
        return new FileHelper(this).stringFromAssetFile("games/game.json");
    }

    private void initializeView() {
        Game game = Configuration.getInstance().getGame();

        View contentView = findViewById(android.R.id.content);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LevelActivity.class));
            }
        });

        UserInterfaceElement mainElement = game.getUserInterface().getMain();
        contentView.setBackgroundColor(
            Color.parseColor(mainElement.getBackgroundColor()));

        UserInterfaceElement titleElement = game.getUserInterface().getTitle();
        TextView guessItTextView = (TextView) findViewById(R.id.main_guessit_text_view);
        guessItTextView.setTextColor(Color.parseColor(
            titleElement.getTextColor()));
        guessItTextView.setShadowLayer(1, 0, -1, Color.parseColor(
            titleElement.getShadowColor()));

        UserInterfaceElement subtitleElement = game.getUserInterface().getSubtitle();
        TextView tapToPlayTextView = (TextView) findViewById(R.id.main_tap_to_play_text_view);
        tapToPlayTextView.setTextColor(Color.parseColor(
            subtitleElement.getTextColor()));
        tapToPlayTextView.setShadowLayer(1, 0, -1, Color.parseColor(
            subtitleElement.getShadowColor()));
    }

}
