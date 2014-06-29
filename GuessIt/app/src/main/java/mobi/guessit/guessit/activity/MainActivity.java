package mobi.guessit.guessit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.model.UserInterface;
import mobi.guessit.guessit.model.UserInterfaceElement;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
    }

    private void initializeView() {
        // TODO: get colors from config json file
        // now I'm  using the colors from Retro Games

        UserInterfaceElement mainElement = new UserInterfaceElement();
        mainElement.setBackgroundColor(Color.parseColor("#262626"));

        UserInterfaceElement titleElement = new UserInterfaceElement();
        titleElement.setTextColor(Color.parseColor("#87ff04"));
        titleElement.setShadowColor(Color.parseColor("#000000"));

        UserInterfaceElement subtitleElement = new UserInterfaceElement();
        subtitleElement.setTextColor(Color.parseColor("#efefef"));
        subtitleElement.setShadowColor(Color.parseColor("#2b2b2b"));

        View contentView = findViewById(android.R.id.content);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LevelActivity.class));
            }
        });
        contentView.setBackgroundColor(mainElement.getBackgroundColor());

        TextView guessItTextView = (TextView) findViewById(R.id.main_guessit_text_view);
        guessItTextView.setTextColor(titleElement.getTextColor());
        guessItTextView.setShadowLayer(1, 0, -1, titleElement.getShadowColor());

        TextView tapToPlayTextView = (TextView) findViewById(R.id.main_tap_to_play_text_view);
        tapToPlayTextView.setTextColor(subtitleElement.getTextColor());
        tapToPlayTextView.setShadowLayer(1, 0, -1, subtitleElement.getShadowColor());
    }

}
