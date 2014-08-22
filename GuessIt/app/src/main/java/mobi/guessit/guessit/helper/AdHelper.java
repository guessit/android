package mobi.guessit.guessit.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import mobi.guessit.guessit.R;

public class AdHelper {

    private static AdHelper instance;

    private AdHelper() {
    }

    public static AdHelper getInstance() {
        if (instance == null) instance = new AdHelper();
        return instance;
    }

    public void loadAds() {
    }

    public void presentAd(Context context) {
        new AlertDialog.Builder(context).
            setMessage("Show Ad!").
            setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // dismiss
                }
            }).create().show();
    }
}
