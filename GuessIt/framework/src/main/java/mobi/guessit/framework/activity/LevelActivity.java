package mobi.guessit.framework.activity;

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

import mobi.guessit.framework.R;
import mobi.guessit.framework.helper.AdHelper;
import mobi.guessit.framework.helper.ColorHelper;
import mobi.guessit.framework.model.Configuration;
import mobi.guessit.framework.model.Level;
import mobi.guessit.framework.model.UserInterfaceElement;
import mobi.guessit.framework.view.LevelView;

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

                        conf.trackEvent(
                            Configuration.Events.GAME_CATEGORY,
                            Configuration.Events.AD_SHOWN,
                            null
                        );
                    }

                    conf.trackEvent(
                        Configuration.Events.GAME_CATEGORY,
                        Configuration.Events.LEVEL_CORRECT,
                        level.getImageName()
                    );

                    if (level.isLastLevel()) {
                        showGameOver(true);
                    }
                }
            });
        }
        return levelView;
    }

    private void showGameOver(boolean animated) {
        Configuration.getInstance().trackEvent(
            Configuration.Events.GAME_CATEGORY,
            Configuration.Events.GAME_OVER,
            null
        );
        setContentView(R.layout.activity_level_game_over);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        setupActionBar();

        Configuration.getInstance().setContext(getApplicationContext());

        Level nextLevel = Configuration.getInstance().getCurrentLevel();
        if (nextLevel.isLastLevel() && nextLevel.isFinished()) {
            showGameOver(false);
        } else {
            getLevelView().setLevel(nextLevel, false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration.getInstance().trackView(Configuration.Views.LEVEL_VIEW);

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
