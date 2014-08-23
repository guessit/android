package mobi.guessit.guessit.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.AdHelper;
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
            levelView.setOnLevelGuessedCorrect(new LevelView.OnLevelGuessedCorrect() {
                @Override
                public void onLevelGuessedCorrect(LevelView levelView, Level level) {
                    Configuration conf = Configuration.getInstance();
                    if (conf.showAds() && conf.isTimeToShowAd()) {
                        AdHelper.getInstance().presentAd(LevelActivity.this);
                        conf.resetCountersAfterShowingAd();
                    }
                }
            });
        }
        return levelView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        setupActionBar();

        Configuration.getInstance().setContext(getApplicationContext());

        Level nextLevel = Configuration.getInstance().getCurrentLevel();
        getLevelView().setLevel(nextLevel, false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Configuration.getInstance().showAds()) {
            AdHelper.getInstance().loadAd(this);
        }
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

        UserInterfaceElement title = Configuration.getInstance().getGame().
            getUserInterface().getTitle();

        TextView guessItTextView = (TextView) layout.findViewById(R.id.action_bar_guessit_text_view);
        guessItTextView.setTextColor(ColorHelper.parseColor(title.getTextColor()));
        guessItTextView.setShadowLayer(1, 0, -1, ColorHelper.parseColor(
            title.getShadowColor()));

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

        Button settingsButton = (Button) layout.findViewById(R.id.action_bar_settings_button);
        settingsButton.setTextColor(navColor);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LevelActivity.this, SettingsActivity.class));
            }
        });

        actionBar.setCustomView(layout);
    }
}
