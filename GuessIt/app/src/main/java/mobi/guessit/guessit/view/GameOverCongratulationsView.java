package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class GameOverCongratulationsView extends RelativeLayout {

    public GameOverCongratulationsView(Context context) {
        super(context);
    }

    public GameOverCongratulationsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameOverCongratulationsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setupUI();
    }

    private void setupUI() {
        UserInterfaceElement ui = Configuration.getInstance().getGame().
            getUserInterface().getGameOver();

        setBackgroundColor(
            ColorHelper.parseColor(ui.getBackgroundColor())
        );

        TextView gameOverLabel = (TextView) findViewById(R.id.game_over);
        gameOverLabel.setTextColor(ColorHelper.parseColor(ui.getSecondaryTextColor()));

        TextView congratsLabel = (TextView) findViewById(R.id.game_over_congratulations);
        congratsLabel.setTextColor(ColorHelper.parseColor(ui.getTextColor()));
        congratsLabel.setShadowLayer(1, 0, -1, ColorHelper.parseColor(
            ui.getShadowColor()));
        congratsLabel.setRotation(-4.f);
        congratsLabel.bringToFront();

        TextView descriptionLabel = (TextView) findViewById(R.id.game_over_description);
        descriptionLabel.setTextColor(ColorHelper.parseColor(ui.getSecondaryTextColor()));
        descriptionLabel.setShadowLayer(1, 0, -1, ColorHelper.parseColor(
            ui.getSecondaryShadowColor()));
        descriptionLabel.setRotation(congratsLabel.getRotation());

        TextView resetProgressLabel = (TextView) findViewById(R.id.game_over_reset_progress);
        resetProgressLabel.setTextColor(ColorHelper.parseColor(ui.getSecondaryTextColor()));
        resetProgressLabel.setShadowLayer(1, 0, -1, ColorHelper.parseColor(
            ui.getSecondaryShadowColor()));
    }
}
