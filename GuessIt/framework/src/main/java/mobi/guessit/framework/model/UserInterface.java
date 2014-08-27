package mobi.guessit.framework.model;

import com.google.gson.annotations.SerializedName;

public class UserInterface {

    private UserInterfaceElement main;
    private UserInterfaceElement navigation;
    private UserInterfaceElement title;
    private UserInterfaceElement subtitle;
    private UserInterfaceElement congratulations;
    private UserInterfaceElement level;
    private UserInterfaceElement answer;
    private UserInterfaceElement placeholder;
    private UserInterfaceElement category;
    private UserInterfaceElement image;
    private UserInterfaceElement frame;
    private UserInterfaceElement keypad;
    private UserInterfaceElement letter;
    private UserInterfaceElement action;
    private UserInterfaceElement help;
    private UserInterfaceElement settings;

    @SerializedName("game_over")
    private UserInterfaceElement gameOver;

    private UserInterfaceElement credits;

    @SerializedName("other_games")
    private UserInterfaceElement otherGames;


    public UserInterfaceElement getMain() {
        return main;
    }

    public void setMain(UserInterfaceElement main) {
        this.main = main;
    }

    public UserInterfaceElement getNavigation() {
        return navigation;
    }

    public void setNavigation(UserInterfaceElement navigation) {
        this.navigation = navigation;
    }

    public UserInterfaceElement getTitle() {
        return title;
    }

    public void setTitle(UserInterfaceElement title) {
        this.title = title;
    }

    public UserInterfaceElement getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(UserInterfaceElement subtitle) {
        this.subtitle = subtitle;
    }

    public UserInterfaceElement getCongratulations() {
        return congratulations;
    }

    public void setCongratulations(UserInterfaceElement congratulations) {
        this.congratulations = congratulations;
    }

    public UserInterfaceElement getLevel() {
        return level;
    }

    public void setLevel(UserInterfaceElement level) {
        this.level = level;
    }

    public UserInterfaceElement getAnswer() {
        return answer;
    }

    public void setAnswer(UserInterfaceElement answer) {
        this.answer = answer;
    }

    public UserInterfaceElement getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(UserInterfaceElement placeholder) {
        this.placeholder = placeholder;
    }

    public UserInterfaceElement getCategory() {
        return category;
    }

    public void setCategory(UserInterfaceElement category) {
        this.category = category;
    }

    public UserInterfaceElement getImage() {
        return image;
    }

    public void setImage(UserInterfaceElement image) {
        this.image = image;
    }

    public UserInterfaceElement getFrame() {
        return frame;
    }

    public void setFrame(UserInterfaceElement frame) {
        this.frame = frame;
    }

    public UserInterfaceElement getKeypad() {
        return keypad;
    }

    public void setKeypad(UserInterfaceElement keypad) {
        this.keypad = keypad;
    }

    public UserInterfaceElement getLetter() {
        return letter;
    }

    public void setLetter(UserInterfaceElement letter) {
        this.letter = letter;
    }

    public UserInterfaceElement getAction() {
        return action;
    }

    public void setAction(UserInterfaceElement action) {
        this.action = action;
    }

    public UserInterfaceElement getHelp() {
        return help;
    }

    public void setHelp(UserInterfaceElement help) {
        this.help = help;
    }

    public UserInterfaceElement getSettings() {
        return settings;
    }

    public void setSettings(UserInterfaceElement settings) {
        this.settings = settings;
    }

    public UserInterfaceElement getGameOver() {
        return gameOver;
    }

    public void setGameOver(UserInterfaceElement gameOver) {
        this.gameOver = gameOver;
    }

    public UserInterfaceElement getCredits() {
        return credits;
    }

    public void setCredits(UserInterfaceElement credits) {
        this.credits = credits;
    }

    public UserInterfaceElement getOtherGames() {
        return otherGames;
    }

    public void setOtherGames(UserInterfaceElement otherGames) {
        this.otherGames = otherGames;
    }
}
