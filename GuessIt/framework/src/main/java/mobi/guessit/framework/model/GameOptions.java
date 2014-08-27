package mobi.guessit.framework.model;

import com.google.gson.annotations.SerializedName;

public class GameOptions {

    private boolean randomize;

    @SerializedName("keypad_rows")
    private int keypadRows;

    @SerializedName("keypad_columns")
    private int keypadColumns;

    @SerializedName("keypad_help_width")
    private int keypadHelpWidth;

    public boolean isRandomize() {
        return randomize;
    }

    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }

    public int getKeypadRows() {
        return keypadRows;
    }

    public void setKeypadRows(int keypadRows) {
        this.keypadRows = keypadRows;
    }

    public int getKeypadColumns() {
        return keypadColumns;
    }

    public void setKeypadColumns(int keypadColumns) {
        this.keypadColumns = keypadColumns;
    }

    public int getKeypadHelpWidth() {
        return keypadHelpWidth;
    }

    public void setKeypadHelpWidth(int keypadHelpWidth) {
        this.keypadHelpWidth = keypadHelpWidth;
    }
}
