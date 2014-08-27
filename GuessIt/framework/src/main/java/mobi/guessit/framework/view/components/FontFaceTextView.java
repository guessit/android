package mobi.guessit.framework.view.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import mobi.guessit.framework.R;

public class FontFaceTextView extends TextView {

    public FontFaceTextView(Context context) {
        super(context);
        initializeView(null);
    }

    public FontFaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(attrs);
    }

    public FontFaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(attrs);
    }

    private void initializeView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs,
                    R.styleable.FontFaceTextView);

            String fontFace = array.getString(R.styleable.FontFaceTextView_fontFace);
            int fontStyle = array.getInteger(R.styleable.FontFaceTextView_fontStyle, Typeface.NORMAL);

            if (fontFace != null) {
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontFace), fontStyle);
            }
        }
    }
}
