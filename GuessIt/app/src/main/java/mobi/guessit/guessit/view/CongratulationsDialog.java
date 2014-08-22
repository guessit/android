package mobi.guessit.guessit.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import mobi.guessit.guessit.R;
import mobi.guessit.guessit.helper.BackgroundHelper;
import mobi.guessit.guessit.helper.ColorHelper;
import mobi.guessit.guessit.model.Configuration;
import mobi.guessit.guessit.model.UserInterfaceElement;

public class CongratulationsDialog extends Dialog {

    public OnDialogDismissed onDialogDismissed;

    public OnDialogDismissed getOnDialogDismissed() {
        return onDialogDismissed;
    }

    public void setOnDialogDismissed(OnDialogDismissed onDialogDismissed) {
        this.onDialogDismissed = onDialogDismissed;
    }

    public CongratulationsDialog setCorrectAnswer(String correctAnswer) {
        getAnswerTextView().setText(correctAnswer);
        return this;
    }

    private TextView answerTextView;
    private TextView getAnswerTextView() {
        if (answerTextView == null) {
            answerTextView = (TextView) findViewById(R.id.alert_answer_text_view);
        }
        return answerTextView;
    }

    public CongratulationsDialog(Context context) {
        super(context);
        setupUI(context);
    }

    public CongratulationsDialog(Context context, int theme) {
        super(context, theme);
        setupUI(context);
    }

    protected CongratulationsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setupUI(context);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismissCongratulationsDialog();
    }

    private void dismissCongratulationsDialog() {
        if (getOnDialogDismissed() != null) {
            getOnDialogDismissed().onDialogDismissed(CongratulationsDialog.this);
        }
    }


    private void setupUI(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.congratulations_dialog);
        getWindow().setBackgroundDrawable(
            new ColorDrawable(android.R.color.transparent));

        View layout = findViewById(R.id.alert_congratulations_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissCongratulationsDialog();
            }
        });

        UserInterfaceElement ui = Configuration.getInstance().getGame().
            getUserInterface().getCongratulations();

        TextView titleTextView = (TextView) findViewById(R.id.alert_congratulations_text_view);
        titleTextView.setTextColor(ColorHelper.parseColor(ui.getTextColor()));
        titleTextView.setShadowLayer(1, 0, -1, ColorHelper.parseColor(ui.getShadowColor()));
        titleTextView.setRotation(-4);

        TextView subtitleTextView = (TextView) findViewById(R.id.alert_correct_answer_text_view);
        subtitleTextView.setRotation(-4);

        PaintDrawable background = new PaintDrawable(ColorHelper.parseColor("#000000"));
        background.setCornerRadius(context.getResources().getDimension(
            R.dimen.congratulations_answer_corner_radius));

        BackgroundHelper.getInstance().setBackground(getAnswerTextView(), background);
    }

    public interface OnDialogDismissed {
        public void onDialogDismissed(CongratulationsDialog dialog);
    }
}
