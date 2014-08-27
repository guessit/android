package mobi.guessit.framework.model;

import com.google.gson.annotations.SerializedName;

public class UserInterfaceElement {

    @SerializedName("background_color")
    private String backgroundColor;

    @SerializedName("color")
    private String color;

    @SerializedName("text_color")
    private String textColor;

    @SerializedName("shadow_color")
    private String shadowColor;

    @SerializedName("secondary_background_color")
    private String secondaryBackgroundColor;

    @SerializedName("secondary_color")
    private String secondaryColor;

    @SerializedName("secondary_text_color")
    private String secondaryTextColor;

    @SerializedName("secondary_shadow_color")
    private String secondaryShadowColor;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
    }

    public String getSecondaryBackgroundColor() {
        return secondaryBackgroundColor;
    }

    public void setSecondaryBackgroundColor(String secondaryBackgroundColor) {
        this.secondaryBackgroundColor = secondaryBackgroundColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getSecondaryTextColor() {
        return secondaryTextColor;
    }

    public void setSecondaryTextColor(String secondaryTextColor) {
        this.secondaryTextColor = secondaryTextColor;
    }

    public String getSecondaryShadowColor() {
        return secondaryShadowColor;
    }

    public void setSecondaryShadowColor(String secondaryShadowColor) {
        this.secondaryShadowColor = secondaryShadowColor;
    }
}
