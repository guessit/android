package mobi.guessit.guessit.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.model.Configuration;

public class HelpView extends Dialog {

    private OnSkipLevelListener onSkipLevelListener;
    private OnEliminateWrongLetterListener onEliminateWrongLetterListener;
    private OnPlaceCorrectLetterListener onPlaceCorrectLetterListener;

    private Button skipLevelButton;
    private Button eliminateWrongLetterButton;
    private Button placeCorrectLetterButton;

    public HelpView(Context context) {
        super(context);
        setupUI(context);
    }

    public HelpView(Context context, int theme) {
        super(context, theme);
        setupUI(context);
    }

    protected HelpView(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setupUI(context);
    }

    private Button getSkipLevelButton() {
        if (skipLevelButton == null) {
            skipLevelButton = (Button) findViewById(R.id.help_skip_button);
        }
        return skipLevelButton;
    }

    private Button getEliminateWrongLetterButton() {
        if (eliminateWrongLetterButton == null) {
            eliminateWrongLetterButton = (Button) findViewById(R.id.help_eliminate_wrong_letter_button);
        }
        return eliminateWrongLetterButton;
    }

    private Button getPlaceCorrectLetterButton() {
        if (placeCorrectLetterButton == null) {
            placeCorrectLetterButton = (Button) findViewById(R.id.help_place_correct_letter_button);
        }
        return placeCorrectLetterButton;
    }

    @Override
    public void show() {
        super.show();

        boolean canSkipLevel = getOnSkipLevelListener().canSkipLevel(this);
        getSkipLevelButton().setEnabled(canSkipLevel);

        boolean canEliminateWrongLetter = getOnEliminateWrongLetterListener().canEliminateWrongLetter(this);
        getEliminateWrongLetterButton().setEnabled(canEliminateWrongLetter);

        boolean canPlaceCorrectLetter = getOnPlaceCorrectLetterListener().canPlaceCorrectLetter(this);
        getPlaceCorrectLetterButton().setEnabled(canPlaceCorrectLetter);
    }

    public OnSkipLevelListener getOnSkipLevelListener() {
        return onSkipLevelListener;
    }

    public void setOnSkipLevelListener(OnSkipLevelListener onSkipLevelListener) {
        this.onSkipLevelListener = onSkipLevelListener;
    }

    public OnEliminateWrongLetterListener getOnEliminateWrongLetterListener() {
        return onEliminateWrongLetterListener;
    }

    public void setOnEliminateWrongLetterListener(OnEliminateWrongLetterListener onEliminateWrongLetterListener) {
        this.onEliminateWrongLetterListener = onEliminateWrongLetterListener;
    }

    public OnPlaceCorrectLetterListener getOnPlaceCorrectLetterListener() {
        return onPlaceCorrectLetterListener;
    }

    public void setOnPlaceCorrectLetterListener(OnPlaceCorrectLetterListener onPlaceCorrectLetterListener) {
        this.onPlaceCorrectLetterListener = onPlaceCorrectLetterListener;
    }

    private void setupUI(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.help_view);

        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams params = window.getAttributes();
        params.windowAnimations = R.style.HelpSlideAnimation;
        params.gravity = Gravity.BOTTOM;

        getSkipLevelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnSkipLevelListener listener = getOnSkipLevelListener();
                if (listener != null) {
                    listener.onSkipLevelRequested(HelpView.this);
                }
            }
        });

        getEliminateWrongLetterButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnEliminateWrongLetterListener listener = getOnEliminateWrongLetterListener();
                if (listener != null) {
                    listener.onEliminateWrongLetterRequested(HelpView.this);
                }
            }
        });

        getPlaceCorrectLetterButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnPlaceCorrectLetterListener listener = getOnPlaceCorrectLetterListener();
                if (listener != null) {
                    listener.onPlaceCorrectLetterRequested(HelpView.this);
                }
            }
        });
    }

    public interface OnSkipLevelListener {
        public void onSkipLevelRequested(HelpView view);

        public boolean canSkipLevel(HelpView view);
    }

    public interface OnEliminateWrongLetterListener {
        public void onEliminateWrongLetterRequested(HelpView view);

        public boolean canEliminateWrongLetter(HelpView view);
    }

    public interface OnPlaceCorrectLetterListener {
        public void onPlaceCorrectLetterRequested(HelpView view);

        public boolean canPlaceCorrectLetter(HelpView view);
    }
}
