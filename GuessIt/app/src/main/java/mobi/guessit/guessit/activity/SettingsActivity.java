package mobi.guessit.guessit.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.activity.adapter.Settings;
import mobi.guessit.guessit.activity.adapter.SettingsArrayAdapter;
import mobi.guessit.guessit.activity.adapter.SettingsType;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class SettingsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Settings[] objs = new Settings[] {
            new Settings(SettingsType.TITLE, R.string.settings_title),
            new Settings(SettingsType.ACTION, R.string.settings_reset)
        };

        SettingsArrayAdapter adapter = new SettingsArrayAdapter(this, objs);

        setListAdapter(adapter);

        setupActionBar();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, "YOO", Toast.LENGTH_SHORT).show();
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
                SettingsActivity.this.onBackPressed();
            }
        });

        Button settingsButton = (Button) layout.findViewById(R.id.action_bar_settings_button);
        settingsButton.setAlpha(0);

        actionBar.setCustomView(layout);
    }
}
