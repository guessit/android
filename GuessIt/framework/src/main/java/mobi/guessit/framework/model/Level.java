package mobi.guessit.framework.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

import mobi.guessit.framework.helper.ResourceHelper;

public class Level {

    @SerializedName("image")
    private String imageName;

    private String category;
    private String url;
    private String bundle;

    private String mAnswer;

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

    public String getKeyName() {
        return "img_" + getImageName();
    }

    public void loadAnswerI18n(Context context) {
        ResourceHelper helper = ResourceHelper.getInstance();
        mAnswer = helper.getString(context, getKeyName()).toUpperCase();
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

    public boolean isLastLevel() {
        return this.equals(Level.guessItLevel());
    }

    public String getLetterAt(int i) {
        return String.valueOf(this.getNoSpacesAnswer().charAt(i));
    }

    private void markFinished() {
        Configuration.getInstance().markLevelFinished(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Level)) return false;

        Level level = (Level) o;

        if (imageName != null ? !imageName.equals(level.imageName) : level.imageName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return imageName != null ? imageName.hashCode() : 0;
    }
}
