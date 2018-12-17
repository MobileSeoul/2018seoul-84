package net.joobjoob.app.seoul_culture_api.Common;

import android.app.Dialog;
import android.content.Context;

import net.joobjoob.app.seoul_culture_api.R;


/**
 * Created by kgh on 2018. 8. 11..
 */


//comment : porgress bar -> Recyclerview Loading
public class CustomProgressDialog extends Dialog {
    private boolean isDismissed;
    public static CustomProgressDialog show(Context context, CharSequence title, CharSequence message) {
        return show(context, title, message, false);
    }

    public static CustomProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, (OnCancelListener)null);
    }

    public static CustomProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, (OnCancelListener)null);
    }

    public static CustomProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.setTitle(title);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    public CustomProgressDialog(Context context) {
        super(context, R.style.AppTheme);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.custom_progress_dialog);
    }


    // change: 전에 여기부분이 에러나서 추가했었는데 기존에 나던 오류 잡고나서 수정하니까 이상없이 돌아가서 다시 주석처리했음

    /*
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismiss();
    }

    @Override
    public void dismiss() {
        if (isDismissed) {
            return;
        }
        try {
            super.dismiss();
        } catch (IllegalArgumentException e) {
            // ignore
        }
        isDismissed = true;
    }
    */

}
