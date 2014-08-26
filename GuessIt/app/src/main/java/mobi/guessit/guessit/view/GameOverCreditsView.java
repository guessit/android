package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class GameOverCreditsView extends RelativeLayout {

    public GameOverCreditsView(Context context) {
        super(context);
    }

    public GameOverCreditsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameOverCreditsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupUI();
    }

    private void setupUI() {
        Game game = Configuration.getInstance().getGame();

        if (game != null) {
            UserInterfaceElement ui = game.getUserInterface().getCredits();

            TextView credits = (TextView) findViewById(R.id.game_over_credits);
            credits.setTextColor(ColorHelper.parseColor(ui.getTextColor()));
            credits.setShadowLayer(1, 0, -1, ColorHelper.parseColor(ui.getShadowColor()));

            TextView thankYou = (TextView) findViewById(R.id.game_over_thank_you);
            thankYou.setTextColor(ColorHelper.parseColor(ui.getTextColor()));
            thankYou.setShadowLayer(1, 0, -1, ColorHelper.parseColor(ui.getShadowColor()));
        }
    }
}
