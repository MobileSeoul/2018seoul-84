package net.joobjoob.app.seoul_culture_api.Common;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kgh on 2018. 8. 11..
 */

//comment: Font , Screen Setting

public class FontUtils {
    private static FontUtils fontUtils;
    private static Typeface mTypeface = null;
    private static Context context;

    public FontUtils(Context context) {
        context = context;
    }

    public static FontUtils getInstance(Context context) {
        if(fontUtils == null) {
            fontUtils = new FontUtils(context);
        }

        if(mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "NotoSansCJKkr-DemiLight.otf");
        }

        return fontUtils;
    }

    public Typeface getmTypeface() {
        return mTypeface;
    }

    public void setGlobalFont(View view) {
        if(view != null && view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup)view;
            int vgCnt = vg.getChildCount();

            for(int i = 0; i < vgCnt; ++i) {
                View v = vg.getChildAt(i);
                if(v instanceof TextView) {
                    ((TextView)v).setTypeface(mTypeface);
                    ((TextView)v).setIncludeFontPadding(false);
                }

                this.setGlobalFont(v);
            }
        }

    }
}