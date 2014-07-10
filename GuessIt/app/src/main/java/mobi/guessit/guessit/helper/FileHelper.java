package mobi.guessit.guessit.helper;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class FileHelper {

    private Context context;

    public FileHelper(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public String stringFromAssetFile(String fileName) {
        String string = null;

        try {
            InputStream inputStream = getContext().getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            string = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }
}
