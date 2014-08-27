package mobi.guessit.framework.helper;

public class StringHelper {

    public static int countOcurrencesOf(String string, String toBeCounted) {
        return string.length() - string.replace(toBeCounted, "").length();
    }

}
