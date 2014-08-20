package mobi.guessit.guessit.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

import mobi.guessit.guessit.helper.ResourceHelper;

public class Level {

    @SerializedName("image")
    private String imageName;

    private String category;
    private String url;
    private String bundle;

    private String mAnswer;
    private transient Drawable mImage;

    public static Level guessItLevel() {
        Level guessIt = new Level();

        guessIt.setImageName("guessit_final");

        return guessIt;
    }

    public GuessingResult guessWithAnswer(String guessingAnswer) {
        GuessingResult result = GuessingResult.WRONG;

        if (guessingAnswer.equals(getNoSpacesAnswer())) {
            result = GuessingResult.CORRECT;
            markFinished();
        }

        return result;
    }

    public void loadResources(Context context) {
        ResourceHelper helper = ResourceHelper.getInstance();

        String keyName = "img_" + getImageName();

        mAnswer = helper.getString(context, keyName).toUpperCase();
        mImage = helper.getImage(context, keyName);
    }

    public Drawable getImage() {
        return mImage;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public String getNoSpacesAnswer() {
        return getAnswer().replaceAll(" ", "");
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public boolean isFinished() {
        return Configuration.getInstance().getFinishedLevelNames().contains(getImageName());
    }

    public String getLetterAt(int i) {
        return String.valueOf(this.getNoSpacesAnswer().charAt(i));
    }

    private void markFinished() {
        Configuration.getInstance().markLevelFinished(this);
    }
}
