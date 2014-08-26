package mobi.guessit.guessit.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class GameOverOtherGamesView extends RelativeLayout {

    public GameOverOtherGamesView(Context context) {
        super(context);
    }

    public GameOverOtherGamesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameOverOtherGamesView(Context context, AttributeSet attrs, int defStyle) {
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
            UserInterfaceElement ui = game.getUserInterface().getOtherGames();

            TextView likedGuessit = (TextView) findViewById(R.id.game_over_liked_guessit);
            likedGuessit.setTextColor(ColorHelper.parseColor(ui.getTextColor()));
            likedGuessit.setShadowLayer(1, 0, -1, ColorHelper.parseColor(ui.getShadowColor()));
            likedGuessit.setRotation(-2.f);

            TextView otherGames = (TextView) findViewById(R.id.game_over_other_games);
            otherGames.setTextColor(ColorHelper.parseColor(ui.getSecondaryTextColor()));
            otherGames.setShadowLayer(1, 0, -1, ColorHelper.parseColor(ui.getSecondaryShadowColor()));
            otherGames.setRotation(-2.f);

            TextView visitWebsite = (TextView) findViewById(R.id.game_over_visit_website);
            visitWebsite.setTextColor(ColorHelper.parseColor(ui.getTextColor()));
            visitWebsite.setShadowLayer(1, 0, -1, ColorHelper.parseColor(ui.getShadowColor()));

            TextView website = (TextView) findViewById(R.id.game_over_website);
            website.setTextColor(ColorHelper.parseColor(ui.getSecondaryTextColor()));
            website.setShadowLayer(1, 0, -1, ColorHelper.parseColor(ui.getSecondaryShadowColor()));

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://guessit.mobi"));
                    getContext().startActivity(intent);
                }
            });
        }
    }
}
