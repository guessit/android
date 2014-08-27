package mobi.guessit.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mobi.guessit.framework.R;
import mobi.guessit.framework.helper.ColorHelper;
import mobi.guessit.framework.model.Configuration;
import mobi.guessit.framework.model.Game;
import mobi.guessit.framework.model.UserInterfaceElement;

public class GameOverCreditsView extends RelativeLayout {

    private Map<String, List<String>> peopleToThank;
    private Map<String, List<String>> getPeopleToThank() {
        if (peopleToThank == null) {
            peopleToThank = new TreeMap<String, List<String>>();

            peopleToThank.put("1_development", Arrays.asList(
                "marlon"
            ));
            peopleToThank.put("2_game_design", Arrays.asList(
                "marlon"
            ));
            peopleToThank.put("3_level_design", Arrays.asList(
                "marlon",
                "rosiene"
            ));
            peopleToThank.put("4_beta_tester", Arrays.asList(
                "maurilio",
                "david",
                "rosiene",
                "fabim",
                "iuri"
            ));
            peopleToThank.put("5_other", Arrays.asList(
                "victor",
                "alexssandro"
            ));
        }
        return peopleToThank;
    }

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

            LinearLayout creditsList = (LinearLayout) findViewById(R.id.game_over_credits_list);

            LayoutInflater inflater = (LayoutInflater) getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (Map.Entry<String, List<String>> entry : getPeopleToThank().entrySet()) {
                String roleId = "game_over_" + entry.getKey().substring(2);

                for(String person : entry.getValue()) {
                    String personId = "game_over_" + person;

                    GameOverCreditsLine creditsLine = (GameOverCreditsLine) inflater.inflate(
                        R.layout.game_over_credits_line, this, false);
                    creditsLine.setInfo(roleId, personId);

                    creditsList.addView(creditsLine);
                }
            }
        }
    }
}
