package mobi.guessit.guessit.model;

public class Level {

    private String imageName;
    private String answer;
    private String category;
    private String url;
    private String bundle;

    private String noSpacesAnswer;

    public GuessingResult guessWithAnswer(String guessingAnswer) {
        return guessingAnswer.equals(getNoSpacesAnswer()) ?
            GuessingResult.CORRECT : GuessingResult.WRONG;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getNoSpacesAnswer() {
        return this.noSpacesAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.noSpacesAnswer = answer.replaceAll(" ", "");
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

    public String getLetterAt(int i) {
        return String.valueOf(this.getNoSpacesAnswer().charAt(i));
    }
}
