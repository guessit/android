package mobi.guessit.guessit.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Level;
import mobi.guessit.guessit.model.UserInterfaceElement;
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

        setupActionBar();

        Level currentDummyLevel = new Level();
        currentDummyLevel.setAnswer("BRAZIL");

        getLevelView().setLevel(currentDummyLevel, false);
    }

    private void setupActionBar() {
        UserInterfaceElement nav = Configuration.getInstance().getGame().
            getUserInterface().getNavigation();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setIcon(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setBackgroundDrawable(new ColorDrawable(ColorHelper.parseColor(
                nav.getBackgroundColor())));

        View layout = LayoutInflater.from(this).inflate(R.layout.action_bar, null);

        final int navColor = ColorHelper.parseColor(nav.getColor());

        Button backButton = (Button) layout.findViewById(R.id.action_bar_back_button);
        backButton.setTextColor(navColor);
        backButton.setText("<");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LevelActivity.this.onBackPressed();
            }
        });

        UserInterfaceElement title = Configuration.getInstance().getGame().
            getUserInterface().getTitle();

        TextView guessItTextView = (TextView) layout.findViewById(R.id.action_bar_guessit_text_view);
        guessItTextView.setTextColor(ColorHelper.parseColor(title.getTextColor()));
        guessItTextView.setShadowLayer(1, 0, -1, ColorHelper.parseColor(
            title.getShadowColor()));

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
}
