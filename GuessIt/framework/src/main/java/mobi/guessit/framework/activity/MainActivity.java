package mobi.guessit.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mobi.guessit.framework.R;
import mobi.guessit.framework.helper.ColorHelper;
import mobi.guessit.framework.helper.FileHelper;
import mobi.guessit.framework.model.Configuration;
import mobi.guessit.framework.model.Game;
import mobi.guessit.framework.model.UserInterfaceElement;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration config = Configuration.getInstance();
        config.setGame(Game.fromJson(loadJSONFile()));
        config.initializeTracker(this);

        initializeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration.getInstance().trackView(Configuration.Views.MAIN_VIEW);
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
            ColorHelper.parseColor(mainElement.getBackgroundColor()));

        UserInterfaceElement titleElement = game.getUserInterface().getTitle();
        TextView guessItTextView = (TextView) findViewById(R.id.main_guessit_text_view);
        guessItTextView.setTextColor(ColorHelper.parseColor(
            titleElement.getTextColor()));
        guessItTextView.setShadowLayer(1, 0, -1, ColorHelper.parseColor(
            titleElement.getShadowColor()));

        UserInterfaceElement subtitleElement = game.getUserInterface().getSubtitle();
        TextView tapToPlayTextView = (TextView) findViewById(R.id.main_tap_to_play_text_view);
        tapToPlayTextView.setTextColor(ColorHelper.parseColor(
            subtitleElement.getTextColor()));
        tapToPlayTextView.setShadowLayer(1, 0, -1, ColorHelper.parseColor(
            subtitleElement.getShadowColor()));
    }

}
