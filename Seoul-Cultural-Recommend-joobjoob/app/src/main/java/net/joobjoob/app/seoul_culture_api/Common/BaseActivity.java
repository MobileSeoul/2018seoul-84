package net.joobjoob.app.seoul_culture_api.Common;
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.app.Activity;



//comment : screen and font setting
public class BaseActivity extends Activity {
    public BaseActivity() {
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        FontUtils.getInstance(this).setGlobalFont(this.getWindow().getDecorView());
    }


}
