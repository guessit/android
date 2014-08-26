package mobi.guessit.guessit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.helper.ResourceHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.Game;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class GameOverCreditsLine extends LinearLayout {

    public GameOverCreditsLine(Context context) {
        super(context);
    }

    public GameOverCreditsLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameOverCreditsLine(Context context, AttributeSet attrs, int defStyle) {
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

            TextView role = (TextView) findViewById(R.id.game_over_credits_role);
            role.setTextColor(ColorHelper.parseColor(ui.getSecondaryTextColor()));

            TextView person = (TextView) findViewById(R.id.game_over_credits_person);
            person.setTextColor(ColorHelper.parseColor(ui.getSecondaryTextColor()));
        }
    }

    public void setInfo(String roleId, String personId) {
        TextView roleTextView = (TextView) findViewById(R.id.game_over_credits_role);
        roleTextView.setText(ResourceHelper.getInstance().getString(getContext(), roleId));

        TextView personTextView = (TextView) findViewById(R.id.game_over_credits_person);
        personTextView.setText(ResourceHelper.getInstance().getString(getContext(), personId));
    }
}
