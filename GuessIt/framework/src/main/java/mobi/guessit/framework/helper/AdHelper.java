package mobi.guessit.framework.helper;

import android.app.Activity;
import android.app.Dialog;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import mobi.guessit.framework.model.Configuration;

public class AdHelper {

    private static AdHelper instance;

    private AdHelper() {
    }

    public static AdHelper getInstance() {
        if (instance == null) instance = new AdHelper();
        return instance;
    }

    private InterstitialAd interstitial;
    private boolean isLoadingAd;

    public InterstitialAd getInterstitial() {
        return interstitial;
    }

    public void setInterstitial(InterstitialAd interstitial) {
        this.interstitial = interstitial;
    }

    public boolean isLoadingAd() {
        return isLoadingAd;
    }

    public void setLoadingAd(boolean isLoadingAd) {
        this.isLoadingAd = isLoadingAd;
    }

    public void loadAd(final Activity context) {
        int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(result, context, 0);

        if (dialog != null) {
            dialog.show();
        }

        if (result == ConnectionResult.SUCCESS && !isLoadingAd()) {
            if (getInterstitial() != null && !getInterstitial().isLoaded()) {
                return;
            }

            InterstitialAd ad = new InterstitialAd(context);
            ad.setAdUnitId(Configuration.getInstance().getGame().getAdUnitId());

            AdRequest adRequest = new AdRequest.Builder().build();

            ad.loadAd(adRequest);
            ad.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    AdHelper.this.setLoadingAd(false);
                    loadAd(context);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    AdHelper.this.setLoadingAd(false);
                }
            });

            setLoadingAd(true);
            setInterstitial(ad);
        }
    }

    public void presentAd(Activity context) {
        InterstitialAd ad = getInterstitial();
        if (ad == null) {
            loadAd(context);
        } else if (ad.isLoaded()) {
            ad.show();
            loadAd(context);
        } else if (!this.isLoadingAd()) {
            loadAd(context);
        }
    }
}
