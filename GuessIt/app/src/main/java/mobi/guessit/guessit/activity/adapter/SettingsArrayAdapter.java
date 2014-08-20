package mobi.guessit.guessit.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class SettingsArrayAdapter extends ArrayAdapter<Settings> {

    private final Settings[] objs;

    public SettingsArrayAdapter(Context context, Settings[] objs) {
        super(context, android.R.layout.simple_list_item_1, objs);
        this.objs = objs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;

        Settings obj = objs[position];

        UserInterfaceElement settings = Configuration.getInstance().getGame().
            getUserInterface().getSettings();

        int backgroundColor;
        int textColor;
        int shadowColor;

        switch (obj.getType()) {
            case TITLE:
                textView = (TextView) LayoutInflater.from(getContext()).
                    inflate(R.layout.settings_title, null);

                backgroundColor = ColorHelper.parseColor(settings.getBackgroundColor());
                textColor       = ColorHelper.parseColor(settings.getTextColor());
                shadowColor     = ColorHelper.parseColor(settings.getShadowColor());

                textView.setBackgroundColor(backgroundColor);
                textView.setTextColor(textColor);
                textView.setShadowLayer(1, 0, -1, shadowColor);

                break;
            case ACTION:
                textView = (TextView) LayoutInflater.from(getContext()).
                    inflate(R.layout.settings_action, null);

                backgroundColor = ColorHelper.parseColor(settings.getSecondaryBackgroundColor());
                textColor       = ColorHelper.parseColor(settings.getSecondaryTextColor());
                shadowColor     = ColorHelper.parseColor(settings.getSecondaryShadowColor());

                textView.setBackgroundColor(backgroundColor);
                textView.setTextColor(textColor);
                textView.setShadowLayer(1, 0, -1, shadowColor);

                break;
        }

        textView.setText(getContext().getResources().getString(obj.getResourceId()));

        return textView;
    }


}
